package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Booking;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Demian on 28.05.2016.
 */
public interface DateUtil
{
    Calendar currentDate();
    Calendar dateMonthAgo();
    Date toDate(String date);
    Date sd(Booking booking, String time);
    String getStringDate(Calendar calendar);
    String toHoursAndMinutes(long milliseconds);
}
