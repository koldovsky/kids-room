package ua.softserveinc.tc.mapper;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.dto.EventDTO;
import ua.softserveinc.tc.entity.Event;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by dima- on 07.05.2016.
 */
@Component
public class EventMapper implements GenericMapper<Event, EventDTO> {
    @Override
    public final Event toEntity(final EventDTO eventDto) {
        Event event = new Event();
        event.setDescription(eventDto.getDescription());
        event.setName(eventDto.getName());
        event.setAgeLow(eventDto.getAgeLow());
        event.setAgeHigh(eventDto.getAgeHigh());

        try {
            DateFormat df = new SimpleDateFormat(DateConst.DATE_FORMAT);
            Date startDate = df.parse(eventDto.getStartTime());
            Date endDate = df.parse(eventDto.getEndTime());

            event.setStartTime(startDate);
            event.setEndTime(endDate);
        } catch (Exception e) {}

        return event;
    }

    @Override
    public final EventDTO toDto(final Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setAgeHigh(event.getAgeHigh());
        eventDTO.setAgeLow(event.getAgeLow());
        eventDTO.setName(event.getName());
        eventDTO.setDescription(event.getDescription());

        DateFormat df = new SimpleDateFormat(DateConst.DATE_FORMAT);

        eventDTO.setStartTime(df.format(event.getStartTime()));
        eventDTO.setEndTime(df.format(event.getEndTime()));

        return eventDTO;
    }
}
