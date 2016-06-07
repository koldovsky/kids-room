package ua.softserveinc.tc.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.dto.EventDtosss;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.service.RoomService;

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
public class EventMapper implements GenericMapper<Event, EventDtosss> {

    @Autowired
    RoomService roomService;

    @Override
    public final Event toEntity(final EventDtosss eventDto) {
        Event event = new Event();
        event.setDescription(eventDto.getDescription());
        event.setName(eventDto.getName());
        if (eventDto.getId() != 0) event.setId(eventDto.getId());
        Date startDate = null;
        Date endDate = null;
        try {
            DateFormat df = new SimpleDateFormat(DateConst.DATE_FORMAT);
            startDate = df.parse(eventDto.getStartTime());
        } catch (ParseException pe) {
            try {
                DateFormat df = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
                startDate = df.parse(eventDto.getStartTime());
            } catch (ParseException e) {
                startDate = null;
                e.printStackTrace();
            }
        }

        try {
            DateFormat df = new SimpleDateFormat(DateConst.DATE_FORMAT);
            endDate = df.parse(eventDto.getEndTime());
        } catch (ParseException pe) {
            try {
                DateFormat df = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
                endDate = df.parse(eventDto.getEndTime());
            } catch (ParseException e) {
                endDate = null;
                e.printStackTrace();
            }
        }

        event.setStartTime(startDate);
        event.setEndTime(endDate);
        event.setRoom(roomService.findById(eventDto.getRoomId()));
        return event;
    }

    @Override
    public final EventDtosss toDto(final Event event) {
        EventDtosss eventDto = new EventDtosss();
        eventDto.setAgeHigh(event.getAgeHigh());
        eventDto.setAgeLow(event.getAgeLow());
        eventDto.setName(event.getName());
        eventDto.setDescription(event.getDescription());
        eventDto.setId(event.getId());

        DateFormat df = new SimpleDateFormat(DateConst.DATE_FORMAT);

        eventDto.setStartTime(df.format(event.getStartTime()));
        eventDto.setEndTime(df.format(event.getEndTime()));

        return eventDto;
    }

    public final List<EventDtosss> toDto(final List<Event> events) {
        List<EventDtosss> result = new LinkedList<EventDtosss>();

        for(Event event : events) {
            result.add(this.toDto(event));
        }

        return result;
    }
}
