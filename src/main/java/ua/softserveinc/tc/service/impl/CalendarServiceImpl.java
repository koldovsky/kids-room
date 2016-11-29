package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.dto.MonthlyEventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.EventMapper;
import ua.softserveinc.tc.mapper.GenericMapper;
import ua.softserveinc.tc.repo.EventRepository;
import ua.softserveinc.tc.service.CalendarService;
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
    private GenericMapper<Event, EventDto> genericMapper;

    public Long create(final Event event) {
        eventDao.create(event);
        return event.getId();
    }

    public final List<EventDto> findEventByRoomId(final long roomId) {
        return eventMapper.toDto(roomService.findById(roomId).getEvents());
    }

    public String getRoomWorkingHours(final long id) {
        return roomService.findById(id).getWorkingHoursStart() +
                " " + roomService.findById(id).getWorkingHoursEnd();
    }

    @Override
    public void add(Event event) {
        eventRepository.saveAndFlush(event);
    }

    public final List<EventDto> createRecurrentEvents(final RecurrentEventDto recurrentEventDto) {
        Date dateForRecurrentStart = DateUtil.toDateISOFormat(recurrentEventDto.getStartTime());
        Date dateForRecurrentEnd = DateUtil.toDateISOFormat(recurrentEventDto.getEndTime());

        List<EventDto> res = new LinkedList<>();

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
        Long newRecID;
        if((eventDao.getMaxRecurrentId() + 1)%10==0)
            newRecID = eventDao.getMaxRecurrentId() + 2;
        else
            newRecID = eventDao.getMaxRecurrentId() + 1;

        while (dateForRecurrentEnd.getTime() > calendar.getTimeInMillis()) {
            for (int i = 0; i < days.length; i++) {
                calendar.set(Calendar.DAY_OF_WEEK, daysOFWeek.get(days[i]));

                if (dateForRecurrentEnd.getTime() < calendar.getTimeInMillis()) break;
                if (dateForRecurrentStart.getTime() > calendar.getTimeInMillis()) continue;

                Event newRecurrentEvent = new Event();
                newRecurrentEvent.setName(recurrentEventDto.getName());
                newRecurrentEvent.setDescription(recurrentEventDto.getDescription());
                newRecurrentEvent.setStartTime(calendar.getTime());


                calendarWithEndDate.setTime(calendar.getTime());
                calendarWithEndDate.set(Calendar.HOUR_OF_DAY, calendarEndTime.get(Calendar.HOUR_OF_DAY));
                calendarWithEndDate.set(Calendar.MINUTE, calendarEndTime.get(Calendar.MINUTE));

                newRecurrentEvent.setEndTime(calendarWithEndDate.getTime());
                newRecurrentEvent.setRecurrentId(newRecID);
                newRecurrentEvent.setRoom(roomDao.findById(recurrentEventDto.getRoomId()));
                newRecurrentEvent.setColor(recurrentEventDto.getColor());

                try {
                    eventDao.create(newRecurrentEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }

                res.add(genericMapper.toDto(newRecurrentEvent));
            }
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.set(Calendar.DAY_OF_WEEK, daysOFWeek.get("Mon"));
        }
        return res;
    }
    //========================== working here
    public final List<EventDto> createMonthlyEvents(final MonthlyEventDto monthlyEventDto) {
        Date dateForMonthlyStart = DateUtil.toDateISOFormat(monthlyEventDto.getStartTime());
        Date dateForMonthlyEnd = DateUtil.toDateISOFormat(monthlyEventDto.getEndTime());
        //// TODO: 29.11.2016 if that data doesnt exist then exception - prevent it 

        List<EventDto> res = new LinkedList<>();

        Calendar calendarEndTime = Calendar.getInstance();
        calendarEndTime.setTime(dateForMonthlyEnd);

        Calendar calendarWithEndDate = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateForMonthlyStart);

        int[] days = monthlyEventDto.getDaysOfMonth();
        Long newRecID = eventDao.getMaxRecurrentId() + 10 - eventDao.getMaxRecurrentId()%10;

        while (dateForMonthlyEnd.getTime() > calendar.getTimeInMillis()) {
            for (int i = 0; i < days.length; i++) {

                if(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)>=days[i])
                    calendar.set(Calendar.DAY_OF_MONTH, days[i]);
                else continue;

                if (dateForMonthlyEnd.getTime() < calendar.getTimeInMillis()) break;
                if (dateForMonthlyStart.getTime() > calendar.getTimeInMillis()) continue;

                Event newRecurrentEvent = new Event();
                newRecurrentEvent.setName(monthlyEventDto.getName());
                newRecurrentEvent.setDescription(monthlyEventDto.getDescription());
                newRecurrentEvent.setStartTime(calendar.getTime());

                calendarWithEndDate.setTime(calendar.getTime());
                calendarWithEndDate.set(Calendar.HOUR_OF_DAY, calendarEndTime.get(Calendar.HOUR_OF_DAY));
                calendarWithEndDate.set(Calendar.MINUTE, calendarEndTime.get(Calendar.MINUTE));

                newRecurrentEvent.setEndTime(calendarWithEndDate.getTime());
                newRecurrentEvent.setRecurrentId(newRecID);
                newRecurrentEvent.setRoom(roomDao.findById(monthlyEventDto.getRoomId()));
                newRecurrentEvent.setColor(monthlyEventDto.getColor());

                try {
                    eventDao.create(newRecurrentEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }

                res.add(genericMapper.toDto(newRecurrentEvent));
            }
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        return res;
    }
    //========================== working here
    public EventDto getRecurrentEventForEditingById(final long recurrentEventId){

        final List<Event> listOfRecurrentEvent = eventDao.getRecurrentEventByRecurrentId(recurrentEventId);
        Set <Integer> weekDays = new HashSet<>();
        Set <Integer> daysOfTheMonth = new HashSet<>();
        Calendar calendar = Calendar.getInstance();
        for (Event event : listOfRecurrentEvent) {
            calendar.setTime(event.getStartTime());
            weekDays.add(calendar.get(Calendar.DAY_OF_WEEK));
            daysOfTheMonth.add(calendar.get(Calendar.DAY_OF_MONTH));
        }
        if (recurrentEventId%10==0) {
            int[] tempDays = new int[daysOfTheMonth.size()];
            int index =0;
            for (int day: daysOfTheMonth)
                tempDays[index++] = day;
            return getMonthlyEventDto(listOfRecurrentEvent, tempDays);
        }
        else
            return getRecurrentEventDto(listOfRecurrentEvent, weekDays);
    }

    public final void updateEvent(final Event event) {
        eventDao.update(event);
    }

    @Override
    public List<Event> findByName(String name) {
        return eventRepository.findByName(name);
    }

    public final void deleteEvent(final Event event) {
        eventDao.delete(event);
    }

}
