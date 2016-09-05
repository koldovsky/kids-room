package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.EventMapper;
import ua.softserveinc.tc.mapper.GenericMapper;
import ua.softserveinc.tc.service.CalendarService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private EventDao eventDao;

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

    public final void updateEvent(final Event event) {
        eventDao.update(event);
    }

    public final void deleteEvent(final Event event) {
        eventDao.delete(event);
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
        Long newRecID = eventDao.getMaxRecurrentId() + 1;


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
                calendarWithEndDate.set(Calendar.HOUR, calendarEndTime.get(Calendar.HOUR));
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

    public String getRoomWorkingHours(final long id) {
        return roomService.findById(id).getWorkingHoursStart() +
                " " + roomService.findById(id).getWorkingHoursEnd();
    }

    public RecurrentEventDto getRecurrentEventForEditingById(long recurrentEventId){

        RecurrentEventDto recurentEventToReturn = new RecurrentEventDto();
        List<Event> listOfRecurrentEvent = new LinkedList<Event>();
        listOfRecurrentEvent = eventDao.getRecurrentEventByRecurrentId(recurrentEventId);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        recurentEventToReturn.setStartTime(df.format(listOfRecurrentEvent.get(0).getStartTime()));
        recurentEventToReturn.setEndTime(df.format(listOfRecurrentEvent.get(listOfRecurrentEvent.size()-1).getEndTime()));;
        recurentEventToReturn.setRecurrentId(recurrentEventId);
        recurentEventToReturn.setColor(listOfRecurrentEvent.get(0).getColor());
        recurentEventToReturn.setName(listOfRecurrentEvent.get(0).getName());
        recurentEventToReturn.setDescription(listOfRecurrentEvent.get(0).getDescription());
        boolean DaysOfWeek[] = {false,false,false,false,false,false};
        Calendar calendar = Calendar.getInstance();
        for (Event event : listOfRecurrentEvent) {
            calendar.setTime(event.getStartTime());
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            DaysOfWeek[day-2]=true;
        }
        String nameOfDays[]=new String[] {"Mon","Tue","Wed","Thu","Fri","Sat"};
        StringBuilder days=new StringBuilder();
        for (int i=0; i < nameOfDays.length; i++){
            if (DaysOfWeek[i]) days.append(" "+nameOfDays[i]);
        }
        recurentEventToReturn.setDaysOfWeek(days.toString());
        return recurentEventToReturn;
    };
}
