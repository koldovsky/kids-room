package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.EventsCreatingResultsDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.dto.MonthlyEventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.EventMapper;
import ua.softserveinc.tc.mapper.GenericMapper;
import ua.softserveinc.tc.service.CalendarService;
import ua.softserveinc.tc.service.EventService;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.Log;
import org.slf4j.Logger;
import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;


import static ua.softserveinc.tc.dto.MonthlyEventDto.getMonthlyEventDto;
import static ua.softserveinc.tc.dto.RecurrentEventDto.getRecurrentEventDto;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private RoomService roomService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventService eventService;

    @Autowired
    private MailService mailService;

    @Log
    private static Logger log;

    @Autowired
    private GenericMapper<Event, EventDto> genericMapper;

    @Override
    public Long create(final Event event) {
        eventDao.create(event);
        return event.getId();
    }

    @Override
    public final List<EventDto> findEventByRoomId(final long roomId) {
        return eventMapper.toDto(roomService.findByIdTransactional(roomId).getEvents());
    }

    @Override
    public String getRoomWorkingHours(final long id) {
        return roomService.findByIdTransactional(id).getWorkingHoursStart() +
                " " + roomService.findByIdTransactional(id).getWorkingHoursEnd();
    }

    @Override
    public String getRoomCapacity(long id) {
        return roomService.findByIdTransactional(id).getCapacity().toString();
    }

    @Override
    public void add(Event event) {
        eventDao.createOrUpdateEvent(event);
    }

    @Override
    public final List<EventDto> createRecurrentEvents(
            final RecurrentEventDto recurrentEventDto) {



        Date dateForRecurrentStart =
                DateUtil.toDateISOFormat(recurrentEventDto.getStartTime());
        Date dateForRecurrentEnd =
                DateUtil.toDateISOFormat(recurrentEventDto.getEndTime());

        List<Event> res = new LinkedList<>();

        Map<String, Integer> daysOFWeek = new HashMap<>();
        daysOFWeek.put("Sun", Calendar.SUNDAY);
        daysOFWeek.put("Mon", Calendar.MONDAY);
        daysOFWeek.put("Tue", Calendar.TUESDAY);
        daysOFWeek.put("Wed", Calendar.WEDNESDAY);
        daysOFWeek.put("Thu", Calendar.THURSDAY);
        daysOFWeek.put("Fri", Calendar.FRIDAY);
        daysOFWeek.put("Sat", Calendar.SATURDAY);

        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.setTime(dateForRecurrentEnd);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateForRecurrentStart);

        String[] days = recurrentEventDto.getDaysOfWeek().split(" ");
        Long newRecID = eventDao.getMaxRecurrentId() + 1;

        while (dateForRecurrentEnd.getTime() > calendar.getTimeInMillis()) {
            for (String day : days) {
                calendar.set(Calendar.DAY_OF_WEEK, daysOFWeek.get(day));

                if (dateForRecurrentEnd.getTime() < calendar.getTimeInMillis()) {
                    break;
                }
                if (dateForRecurrentStart.getTime() > calendar.getTimeInMillis()) {
                    continue;
                }

                Event newRecurrentEvent = genericMapper.toEntity(recurrentEventDto);
                newRecurrentEvent.setRecurrentType(
                        EventConstants.TypeOfRecurentEvent.WEEKLY);
                newRecurrentEvent.setTime(calendar, calendarEndDate);
                newRecurrentEvent.setRecurrentId(newRecID);

                res.add(newRecurrentEvent);

            }
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.set(Calendar.DAY_OF_WEEK, daysOFWeek.get("Mon"));

        }
        eventDao.saveSetOfEvents(res);

        if(recurrentEventDto.getRecurrentId() != null)
            sendNotifyForRecurrent(res);

        return eventService.getListOfEventDto(res);
    }

    public final EventsCreatingResultsDto createMonthlyEvents(final MonthlyEventDto monthlyEventDto) {
        Date dateForMonthlyStart = DateUtil.toDateISOFormat(monthlyEventDto.getStartTime());
        Date dateForMonthlyEnd = DateUtil.toDateISOFormat(monthlyEventDto.getEndTime());
        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.setTime(dateForMonthlyEnd);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateForMonthlyStart);
        List<String> daysWerentCreated = new LinkedList<>();
        List<Event> res = new LinkedList<>();
        Set<Integer> days = monthlyEventDto.getDaysOfMonth();
        Long newRecID = eventDao.getMaxRecurrentId() + 1;
        long endDate = calendarEndDate.getTimeInMillis();

        while (calendar.getTimeInMillis() <= endDate) {
            if (days.contains(calendar.get(Calendar.DAY_OF_MONTH))) {
                Event newRecurrentEvent = genericMapper.toEntity(monthlyEventDto);
                newRecurrentEvent.setRecurrentType(EventConstants.TypeOfRecurentEvent.MONTHLY);
                newRecurrentEvent.setRecurrentId(newRecID);
                newRecurrentEvent.setTime(calendar, calendarEndDate);
                res.add(newRecurrentEvent);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendar.setTime(dateForMonthlyStart);
        while (calendar.getTimeInMillis() <= endDate) {
            monthlyEventDto.getDaysOfMonth().stream()
                    .filter(day -> calendar.getActualMaximum(Calendar.DAY_OF_MONTH) < day)
                    .forEach(day -> daysWerentCreated.add(day + "/" + (calendar.get(Calendar.MONTH) + 1) +
                            "/" + calendar.get(Calendar.YEAR)));
            calendar.add(Calendar.MONTH, 1);
        }

        eventDao.saveSetOfEvents(res);
        if (monthlyEventDto.getRecurrentId() != null)
            sendNotifyForRecurrent(res);

        return new EventsCreatingResultsDto(eventService.getListOfEventDto(res), daysWerentCreated);
    }

    @Override
    public EventDto getRecurrentEventForEditingById(
            final long recurrentEventId) {

        final List<Event> listOfRecurrentEvent =
                eventDao.getRecurrentEventByRecurrentId(recurrentEventId);
        Calendar calendar = Calendar.getInstance();

        if (listOfRecurrentEvent.get(0).getRecurrentType() ==
                EventConstants.TypeOfRecurentEvent.MONTHLY) {
            Set<Integer> daysOfTheMonth = new HashSet<>();
            for (Event event : listOfRecurrentEvent) {
                calendar.setTime(event.getStartTime());
                daysOfTheMonth.add(calendar.get(Calendar.DAY_OF_MONTH));
            }
            return getMonthlyEventDto(listOfRecurrentEvent, daysOfTheMonth);
        } else {
            Set<Integer> weekDays = new HashSet<>();
            for (Event event : listOfRecurrentEvent) {
                calendar.setTime(event.getStartTime());
                weekDays.add(calendar.get(Calendar.DAY_OF_WEEK));
            }
            return getRecurrentEventDto(listOfRecurrentEvent, weekDays);
        }
    }

    @Override
    public final void createOrUpdateEvent(final Event event) {
        if(event.getId() != null && event.getRecurrentId() == null){
            sendNotifyForSingle(eventService.findEntityById(event.getId()), event);
        }
        eventDao.createOrUpdateEvent(event);
    }

    @Override
    public List<Event> findByName(String name) {
        return eventService.findByName(name);
    }

    @Override
    public final void deleteEvent(final Event event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstants.DATE_AND_TIME_FORMAT);
        List<String> emails = eventService.getEmailForNotifyChangeEvent(event.getId());
        String datePeriod = dateFormat.format(event.getStartTime()) + " - " + dateFormat.format(event.getEndTime());

        if (emails.size() > 0) {
            try {
                if (event.getRecurrentId() != null)
                    mailService.sendNotifyChangeEvent(emails, event.getName(), datePeriod, EventConstants.Messages.CANCEL_EVENT_MSG, null);
                else
                    mailService.sendNotifyChangeEvent(emails, event.getName(), datePeriod, EventConstants.Messages.CANCEL_SERIES_EVENT_MSG, null);

            } catch (MessagingException e) {
                log.error("Notify email about cancel event didn't send. ", e);
            }
        }

        eventDao.delete(event);
    }

    void sendNotifyForSingle(Event event, Event eventNew) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstants.DATE_AND_TIME_FORMAT);
        List<String> emails = eventService.getEmailForNotifyChangeEvent(event.getId());
        String datePeriod = dateFormat.format(event.getStartTime()) + " - " + dateFormat.format(event.getEndTime());

        StringBuilder field = new StringBuilder();
        field.append(EventConstants.Messages.CHANGE_FIELD_MSG).append("<br>");
        String message = null;
        message = EventConstants.Messages.CHANGE_EVENT_MSG;
        Map<String, String> compareMap = eventDao.compareAndGetField(event, eventNew);
        for (String key : compareMap.keySet()) {
            field.append(key).append(": ").append(compareMap.get(key)).append("<br>");
        }

        if (emails.size() > 0) {
            try {
                mailService.sendNotifyChangeEvent(emails, event.getName(), datePeriod, message, field.toString());
            } catch (MessagingException e) {
                log.error("Notify email about change event didn't send. ", e);
            }

        }
    }

    void sendNotifyForRecurrent(List<Event> eventList) {
        eventList.forEach(event -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstants.DATE_AND_TIME_FORMAT);
            List<String> emails = eventService.getEmailForNotifyChangeEvent(event.getId());
            String datePeriod = dateFormat.format(event.getStartTime()) + " - " + dateFormat.format(event.getEndTime());

            StringBuilder field = new StringBuilder();
            field.append(EventConstants.Messages.CURRENT_INFO_MSG).append("<br>");
            field.append(EventConstants.Entity.NAME).append(": ").append(event.getName()).append("<br>");
            field.append(EventConstants.Entity.START_TIME).append(": ").append(dateFormat.format(event.getStartTime())).append("<br>");
            field.append(EventConstants.Entity.END_TIME).append(": ").append(dateFormat.format(event.getEndTime())).append("<br>");
            field.append(EventConstants.Entity.ID_ROOM).append(": ").append(event.getRoom().getName()).append("<br>");
            if (event.getDescription() != null)
                field.append(EventConstants.Entity.DESCRIPTION).append(": ").append(event.getDescription()).append("<br>");

            if (emails.size() > 0) {
                try {
                    mailService.sendNotifyChangeEvent(emails, event.getName(), datePeriod, EventConstants.Messages.CHANGE_SERIES_EVENT_MSG, field.toString());
                } catch (MessagingException e) {
                    log.error("Notify email about change event didn't send. ", e);
                }

            }
        });

    }
}
