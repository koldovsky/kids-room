package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.service.DayOffService;
import ua.softserveinc.tc.util.AutowireHelper;

import javax.persistence.PostPersist;
import java.time.LocalDate;

import static ua.softserveinc.tc.constants.DateConstants.WEEK_LENGTH;
import static ua.softserveinc.tc.constants.DayOffConstants.Event.DAY_OFF_DESCRIPTION;
import static ua.softserveinc.tc.constants.DayOffConstants.Event.EVENT_COLOR;
import static ua.softserveinc.tc.util.LocalDateUtil.asDate;

public class DayAddedListener {

    @Autowired
    private DayOffService dayOffService;

    @Autowired
    private CalendarServiceImpl calendarService;

    @PostPersist
    public void postPersist(DayOff day) {
        LocalDate today = LocalDate.now();

        AutowireHelper.autowire(this, this.calendarService);
        AutowireHelper.autowire(this, this.dayOffService);

        if (today.until(day.getStartDate()).getDays() < WEEK_LENGTH) {
            dayOffService.sendSingleMail(day);
            day.getRooms().forEach(room -> calendarService.add(Event.builder()
                    .name(day.getName())
                    .startTime(asDate(day.getStartDate()))
                    .endTime(asDate(day.getEndDate()))
                    .room(room)
                    .color(EVENT_COLOR)
                    .description(DAY_OFF_DESCRIPTION)
                    .build()));
        }

    }

    public DayAddedListener() {
    }
}
