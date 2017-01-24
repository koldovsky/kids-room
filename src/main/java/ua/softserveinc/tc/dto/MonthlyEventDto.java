package ua.softserveinc.tc.dto;

import org.springframework.beans.factory.annotation.Autowired;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.GenericMapper;

import java.util.List;
import java.util.Set;


public class MonthlyEventDto extends EventDto{
    @Autowired
    private GenericMapper<Event, EventDto> genericMapper;

    private Set<Integer> daysOfTheMonth;

    public MonthlyEventDto() {
    }

    public MonthlyEventDto(Event event) {
        super(event);
    }

    public MonthlyEventDto(EventDto event) {
        super(event);
    }

    public Set<Integer> getDaysOfTheMonth() {
        return daysOfTheMonth;
    }

    public void setDaysOfTheMonth(Set<Integer> daysOfTheMonth) {
        this.daysOfTheMonth = daysOfTheMonth;
    }

    public static MonthlyEventDto getMonthlyEventDto(List<Event> listOfRecurrentEvent,
                                                     Set<Integer> daysOfTheMonth) {
        Event startDay = listOfRecurrentEvent.get(0);
        Event endDay = listOfRecurrentEvent.get(listOfRecurrentEvent.size() - 1);
        startDay.setEndTime(endDay.getEndTime());
        MonthlyEventDto monthlyEventDto = new MonthlyEventDto(startDay);
        monthlyEventDto.setDaysOfTheMonth(daysOfTheMonth);
        return monthlyEventDto;
    }

    public void setDaysOfMonth(Set<Integer> daysOfTheMonth) {
        this.daysOfTheMonth = daysOfTheMonth;
    }

    public Set<Integer> getDaysOfMonth() {
        return daysOfTheMonth;
    }

}
