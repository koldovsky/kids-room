package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.EventsCreatingResultsDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.dto.MonthlyEventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.EventMapper;
import ua.softserveinc.tc.mapper.GenericMapper;
import ua.softserveinc.tc.repo.EventRepository;
import ua.softserveinc.tc.service.CalendarService;
import ua.softserveinc.tc.service.EventService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.DateUtil;

import java.util.*;

import static ua.softserveinc.tc.dto.MonthlyEventDto.getMonthlyEventDto;
import static ua.softserveinc.tc.dto.RecurrentEventDto.getRecurrentEventDto;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventService eventService;

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
        eventRepository.saveAndFlush(event);
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
        return eventService.getListOfEventDto(res);
    }

    public final EventsCreatingResultsDto createMonthlyEvents(
            final MonthlyEventDto monthlyEventDto) {

        Date dateForMonthlyStart =
                DateUtil.toDateISOFormat(monthlyEventDto.getStartTime());
        Date dateForMonthlyEnd =
                DateUtil.toDateISOFormat(monthlyEventDto.getEndTime());

        List<String> daysWerentCreated = new LinkedList<>();
        List<Event> res = new LinkedList<>();

        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.setTime(dateForMonthlyEnd);
        Calendar calendarStartDate = Calendar.getInstance();
        calendarStartDate.setTime(dateForMonthlyStart);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateForMonthlyStart);

        Set<Integer> days = monthlyEventDto.getDaysOfTheMonth();
        Long newRecID = eventDao.getMaxRecurrentId() + 1;

        while (dateForMonthlyEnd.getTime() > calendar.getTimeInMillis()) {
            for (int day : days) {

                if ((calendar.get(Calendar.YEAR) == calendarStartDate.get(Calendar.YEAR)) &&
                        (calendar.get(Calendar.MONTH) == calendarStartDate.get(Calendar.MONTH)) &&
                        (calendarStartDate.get(Calendar.DAY_OF_MONTH) > day)) {
                    continue;
                }
                if ((calendar.get(Calendar.YEAR) == calendarEndDate.get(Calendar.YEAR)) &&
                        (calendarEndDate.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) &&
                        (calendarEndDate.get(Calendar.DAY_OF_MONTH) < day)) {
                    break;
                }

                if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) >= day) {
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                } else {
                    daysWerentCreated.add(day + "/" + (calendar.get(Calendar.MONTH) + 1) +
                            "/" + calendar.get(Calendar.YEAR));
                    continue;
                }

                Event newRecurrentEvent = genericMapper.toEntity(monthlyEventDto);
                newRecurrentEvent.setRecurrentType(
                        EventConstants.TypeOfRecurentEvent.MONTHLY);
                newRecurrentEvent.setRecurrentId(newRecID);
                newRecurrentEvent.setTime(calendar, calendarEndDate);

                res.add(newRecurrentEvent);
            }
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        eventDao.saveSetOfEvents(res);

        return new EventsCreatingResultsDto(
                eventService.getListOfEventDto(res), daysWerentCreated);
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
        eventDao.createOrUpdateEvent(event);
    }

    @Override
    public List<Event> findByName(String name) {
        return eventService.findByName(name);
    }

    @Override
    public final void deleteEvent(final Event event) {
        eventDao.delete(event);
    }

}
