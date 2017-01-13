package ua.softserveinc.tc.util;

import java.text.ParseException;

public class CalendarUtils {

    public static final Object[] getEvents() throws ParseException {
        return new Object[]{
                new Object[]{EventUtils.getListOfEvents().get(0), 0L},
                new Object[]{EventUtils.getListOfEvents().get(1), 1L},
                new Object[]{EventUtils.getListOfEvents().get(2), 2L}
        };
    }

}
