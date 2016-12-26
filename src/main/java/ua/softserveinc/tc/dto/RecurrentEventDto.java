package ua.softserveinc.tc.dto;

import org.springframework.beans.factory.annotation.Autowired;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.GenericMapper;

import java.util.List;
import java.util.Set;

public class RecurrentEventDto extends EventDto {

    @Autowired
    private GenericMapper<Event, EventDto> genericMapper;

    private String daysOfWeek;

    private Set<Integer> weekDays;

    public RecurrentEventDto() {
    }

    public RecurrentEventDto(Event event) {
        super(event);
    }

    public RecurrentEventDto(EventDto event) {
        super(event);
    }

    public static RecurrentEventDto getRecurrentEventDto(List<Event> listOfRecurrentEvent,
                                                         Set<Integer> weekDays) {
        Event startDay = listOfRecurrentEvent.get(0);
        Event endDay = listOfRecurrentEvent.get(listOfRecurrentEvent.size() - 1);
        startDay.setEndTime(endDay.getEndTime());
        RecurrentEventDto recurrentEventDto = new RecurrentEventDto(startDay);
        recurrentEventDto.setWeekDays(weekDays);
        return recurrentEventDto;
    }

    public void setWeekDays(Set<Integer> weekDays) {
        this.weekDays = weekDays;

    }

    public Set<Integer> getWeekDays() {
        return weekDays;
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
