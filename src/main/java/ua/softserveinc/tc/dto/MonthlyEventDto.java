package ua.softserveinc.tc.dto;

import org.springframework.beans.factory.annotation.Autowired;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.GenericMapper;

import java.util.List;

/**
 * Created by IhorTovpinets on 29.11.2016.
 */
public class MonthlyEventDto extends EventDto{
    @Autowired
    private GenericMapper<Event, EventDto> genericMapper;

    private int[] daysOfTheMonth;

    public MonthlyEventDto() {
    }

    public MonthlyEventDto(Event event) {
        super(event);
    }

    public static MonthlyEventDto getMonthlyEventDto(List<Event> listOfRecurrentEvent,
                                                     int[] daysOfTheMonth) {
        Event startDay = listOfRecurrentEvent.get(0);
        Event endDay = listOfRecurrentEvent.get(listOfRecurrentEvent.size() - 1);
        startDay.setEndTime(endDay.getEndTime());
        MonthlyEventDto monthlyEventDto = new MonthlyEventDto(startDay);
        monthlyEventDto.setDaysOfTheMonth(daysOfTheMonth);
        return monthlyEventDto;
    }

    public void setDaysOfTheMonth(int[] daysOfTheMonth) {
        this.daysOfTheMonth = daysOfTheMonth;
    }

    public int[] getDaysOfTheMonth() {
        return daysOfTheMonth;
    }

}
