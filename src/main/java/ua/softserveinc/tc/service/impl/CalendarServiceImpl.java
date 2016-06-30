package ua.softserveinc.tc.service.impl;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.EventMapper;
import ua.softserveinc.tc.service.CalendarService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.DateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

/**
 * Created by dima- on 07.05.2016.
 */
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

    public Long create(final Event event) {
        eventDao.create(event);
        return event.getId();
    }

    public final List<EventDto> findByRoomId(final long roomId) {
        return eventMapper.toDto(roomService.findById(roomId).getEvents());
    }

    public final void updateEvent(Event event) {
        eventDao.update(event);
    }

    public final void deleteEvent(Event event) {
        eventDao.delete(event);
    }

    //TODO: Refactor this method

    public final List<Long> createRecurrentEvents(RecurrentEventDto recurrentEventDto) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        String dateStart = recurrentEventDto.getStartTime();
        String dateEnd = recurrentEventDto.getEndTime();

        Date dateForRecurrentStart;
        Date dateForRecurrentEnd;

        List<Long> res = new ArrayList<>();

        Map<String, Integer> daysOFWeek = new HashMap<>();
        daysOFWeek.put("Sun", 1);
        daysOFWeek.put("Mon", 2);
        daysOFWeek.put("Tue", 3);
        daysOFWeek.put("Wed", 4);
        daysOFWeek.put("Thu", 5);
        daysOFWeek.put("Fri", 6);
        daysOFWeek.put("Sat", 7);

        try{
            dateForRecurrentStart = sdf.parse(dateStart);
            dateForRecurrentEnd = sdf.parse(dateEnd);
        } catch (Exception e) {
            dateForRecurrentStart = null;
            dateForRecurrentEnd = null;
        }

        Calendar calendarEndTime = Calendar.getInstance();
        calendarEndTime.setTime(dateForRecurrentEnd);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateForRecurrentStart);

        String[] days = recurrentEventDto.getDaysOfWeek().split(" ");
        Long newRecID = eventDao.getMaxRecurrentId() + 1;


        while (dateForRecurrentEnd.getTime() > calendar.getTimeInMillis()) {
            for(int i = 0; i < days.length; i++) {
                calendar.set(Calendar.DAY_OF_WEEK, daysOFWeek.get(days[i]));

                if(dateForRecurrentEnd.getTime() < calendar.getTimeInMillis()) break;
                if(dateForRecurrentStart.getTime() > calendar.getTimeInMillis()) continue;

                Event newRecurrentEvent = new Event();
                newRecurrentEvent.setName(recurrentEventDto.getName());
                newRecurrentEvent.setDescription(recurrentEventDto.getDescription());
                newRecurrentEvent.setStartTime(calendar.getTime());

                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(calendar.getTime());
                calendar1.set(Calendar.HOUR, calendarEndTime.get(Calendar.HOUR));
                calendar1.set(Calendar.MINUTE, calendarEndTime.get(Calendar.MINUTE));

                newRecurrentEvent.setEndTime(calendar1.getTime());

                newRecurrentEvent.setRecurrentId(newRecID);
                newRecurrentEvent.setRoom(roomDao.findById(recurrentEventDto.getRoomId()));
                newRecurrentEvent.setColor(recurrentEventDto.getColor());
                res.add(this.create(newRecurrentEvent));
            }
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.set(Calendar.DAY_OF_WEEK, daysOFWeek.get("Mon"));
        }
        return res;
    }
}
