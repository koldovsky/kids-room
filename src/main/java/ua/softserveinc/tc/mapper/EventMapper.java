package ua.softserveinc.tc.mapper;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dima- on 07.05.2016.
 */
@Component
public class EventMapper implements GenericMapper<Event, EventDto> {

    @Autowired
    RoomService roomService;

    @Autowired
    RoomDao roomDao;

    @Log
    private static Logger log;


    @Override
    public final Event toEntity(final EventDto eventDto) {

        Event event = new Event();

        event.setDescription(eventDto.getDescription());
        event.setName(eventDto.getName());
        if (eventDto.getId() != 0) {
            event.setId(eventDto.getId());
        }
        Date startDate;
        Date endDate;

        try {
            DateFormat df = new SimpleDateFormat(DateConstants.DATE_FORMAT);
            startDate = df.parse(eventDto.getStartTime());
            endDate = df.parse(eventDto.getEndTime());
        } catch (ParseException e) {
            startDate = null;
            endDate = null;
            log.error("Error parse simpleDateFormat", e);
        }

        event.setStartTime(startDate);
        event.setEndTime(endDate);

        event.setRoom(roomDao.findById(eventDto.getRoomId()));

        return event;
    }

    @Override
    public final EventDto toDto(final Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setAgeHigh(event.getAgeHigh());
        eventDto.setAgeLow(event.getAgeLow());
        eventDto.setName(event.getName());
        eventDto.setDescription(event.getDescription());
        eventDto.setId(event.getId());

        DateFormat df = new SimpleDateFormat(DateConstants.DATE_FORMAT);

        eventDto.setStartTime(df.format(event.getStartTime()));
        eventDto.setEndTime(df.format(event.getEndTime()));

        return eventDto;
    }

    public final List<EventDto> toDto(final List<Event> events) {
        List<EventDto> result = new LinkedList<>();

        for (Event event : events) {
            result.add(this.toDto(event));
        }

        return result;
    }
}
