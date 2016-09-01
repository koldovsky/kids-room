package ua.softserveinc.tc.dto;

import org.springframework.beans.factory.annotation.Autowired;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.GenericMapper;

/**
 * Created by dima- on 28.06.2016.
 */
public class RecurrentEventDto extends EventDto{

    @Autowired
    private GenericMapper<Event, EventDto> genericMapper;

    private String daysOfWeek;

    public RecurrentEventDto() {
    }

    public RecurrentEventDto(Event event) {
        super(event);
    }
    public Event toEvent() {
        EventDto eventDto = new EventDto();
        eventDto.setName(this.getName());
        eventDto.setDescription(this.getDescription());
        eventDto.setStartTime(this.getStartTime());
        eventDto.setEndTime(this.getEndTime());
        eventDto.setRoomId(this.getRoomId());

        if (this.getId() != 0) {
            eventDto.setId(this.getId());
        }
        return genericMapper.toEntity(eventDto);
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

}
