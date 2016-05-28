package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Booking;

import java.util.Date;

/**
 * Created by Demian on 28.05.2016.
 */
public interface DateUtil
{
    Date toDate(String date);
    Date sd(Booking booking, String time);
    String toHoursAndMinutes(long milliseconds);
}
