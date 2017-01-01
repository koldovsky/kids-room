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
    private RoomDao roomDao;

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
        return eventMapper.toDto(roomService.findById(roomId).getEvents());
    }

    @Override
    public String getRoomWorkingHours(final long id) {
        return roomService.findById(id).getWorkingHoursStart() +
                " " + roomService.findById(id).getWorkingHoursEnd();
    }

    @Override
    public String getRoomCapacity(long id) {
        return roomService.findById(id).getCapacity().toString();
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

        Calendar calendarEndTime = Calendar.getInstance();
        calendarEndTime.setTime(dateForRecurrentEnd);

        Calendar calendarWithEndDate = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateForRecurrentStart);

        String[] days = recurrentEventDto.getDaysOfWeek().split(" ");
        Long newRecID = eventDao.getMaxRecurrentId() + 1;

        while (dateForRecurrentEnd.getTime() > calendar.getTimeInMillis()) {
            for (String day : days) {
                calendar.set(Calendar.DAY_OF_WEEK, daysOFWeek.get(day));

                if (dateForRecurrentEnd.getTime() <
                        calendar.getTimeInMillis())
                    break;
                if (dateForRecurrentStart.getTime() >
                        calendar.getTimeInMillis())
                    continue;

                Event newRecurrentEvent = new Event();
                newRecurrentEvent.setName(recurrentEventDto.getName());
                newRecurrentEvent.setDescription(
                        recurrentEventDto.getDescription());
                newRecurrentEvent.setStartTime(calendar.getTime());
                newRecurrentEvent.setRecurrentType(
                        EventConstants.TypeOfRecurentEvent.WEEKLY);

                calendarWithEndDate.setTime(calendar.getTime());
                calendarWithEndDate.set(
                        Calendar.HOUR_OF_DAY,
                        calendarEndTime.get(Calendar.HOUR_OF_DAY));
                calendarWithEndDate.set(
                        Calendar.MINUTE,
                        calendarEndTime.get(Calendar.MINUTE));

                newRecurrentEvent.setEndTime(calendarWithEndDate.getTime());
                newRecurrentEvent.setRecurrentId(newRecID);
                newRecurrentEvent.setRoom(
                        roomDao.findById(recurrentEventDto.getRoomId()));
                newRecurrentEvent.setColor(recurrentEventDto.getColor());

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

        Calendar calendarWithEndTime = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateForMonthlyStart);

        Set<Integer> days = monthlyEventDto.getDaysOfTheMonth();
        Long newRecID = eventDao.getMaxRecurrentId() + 1;

        while (dateForMonthlyEnd.getTime() > calendar.getTimeInMillis()) {
            for (int day : days) {

                if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) >= day) {
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                } else {
                    if( (calendarEndDate.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)) &&
                            (calendarEndDate.get(Calendar.DAY_OF_MONTH)<day)) {
                        break;
                    } else {
                        daysWerentCreated.add(day + "/" + (calendar.get(Calendar.MONTH) + 1) +
                                "/" + calendar.get(Calendar.YEAR));
                        continue;
                    }
                }

                if (dateForMonthlyStart.getTime() >
                        calendar.getTimeInMillis()) {
                    continue;
                }
                if (dateForMonthlyEnd.getTime() <
                        calendar.getTimeInMillis()) {
                    break;
                }

                Event newRecurrentEvent = new Event();
                newRecurrentEvent.setName(monthlyEventDto.getName());
                newRecurrentEvent.setDescription(
                        monthlyEventDto.getDescription());
                newRecurrentEvent.setStartTime(calendar.getTime());

                calendarWithEndTime.setTime(calendar.getTime());
                calendarWithEndTime.set(Calendar.HOUR_OF_DAY,
                        calendarEndDate.get(Calendar.HOUR_OF_DAY));
                calendarWithEndTime.set(Calendar.MINUTE,
                        calendarEndDate.get(Calendar.MINUTE));

                newRecurrentEvent.setEndTime(calendarWithEndTime.getTime());
                newRecurrentEvent.setRecurrentId(newRecID);
                newRecurrentEvent.setRoom(
                        roomDao.findById(monthlyEventDto.getRoomId()));
                newRecurrentEvent.setColor(monthlyEventDto.getColor());
                newRecurrentEvent.setRecurrentType(
                        EventConstants.TypeOfRecurentEvent.MONTHLY);

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
    public final void updateEvent(final Event event) {
        eventDao.update(event);
    }

    @Override
    public List<Event> findByName(String name) {
        return eventRepository.findByName(name);
    }

    @Override
    public final void deleteEvent(final Event event) {
        eventDao.delete(event);
    }

}
