package ua.softserveinc.tc.service.impl;

import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.service.DayOffService;

import javax.persistence.PostPersist;
import java.time.LocalDate;

import static ua.softserveinc.tc.constants.DateConstants.WEEK_LENGTH;


public class DayAddedListener {

    public DayAddedListener() {
        dayOffService = new DayOffServiceImpl();
    }

    private DayOffService dayOffService;

    @PostPersist
    public void checkDayMailSending(DayOff day) {
        LocalDate today = LocalDate.now();

        if (today.until(day.getStartDate()).getDays() < WEEK_LENGTH) {
            dayOffService.sendSingleMail(day);
        }

    }

}
