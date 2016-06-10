package ua.softserveinc.tc.util;

import ua.softserveinc.tc.constants.DateConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Demian on 07.06.2016.
 */
public final class DateUtil {
    private static DateFormat dateFormat = new SimpleDateFormat(DateConstants.SHORT_DATE_FORMAT);
    private static DateFormat dateAndTimeFormat = new SimpleDateFormat(DateConstants.DATE_AND_TIME_FORMAT);
    private static DateFormat ISODateFormat = new SimpleDateFormat(DateConstants.DATE_FORMAT);

    // Suppresses default constructor, ensuring non-instantiability.
    private DateUtil() {
    }

    public static Date toDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            //TODO: log
            return null;
        }
    }

    public static Date toDateAndTime(String date) {
        try {
            return dateAndTimeFormat.parse(date);
        } catch (ParseException e) {
            //TODO: log
            return null;
        }
    }

    public static Date toDateISOFormat(String dateToParse) {
        try {
            return ISODateFormat.parse(dateToParse);
        } catch (ParseException e) {
            //TODO: log
            return null;
        }
    }

    private static int getHoursFromMilliseconds(long milliseconds) {
        return (int) TimeUnit.MILLISECONDS.toHours(milliseconds);
    }

    private static int getMinutesFromMilliseconds(long milliseconds) {
        int hours = getHoursFromMilliseconds(milliseconds);
        return (int) TimeUnit.MILLISECONDS.toMinutes(milliseconds - TimeUnit.HOURS.toMillis(hours));
    }

    public static int getRoundedHours(long milliseconds) {
        int hours = getHoursFromMilliseconds(milliseconds);
        int minutes = getMinutesFromMilliseconds(milliseconds);

        // 02:00 hours - 2 hours; 02:01 hours - 3 hours
        if (minutes > 0) hours++;

        return hours;
    }

    public static String toHoursAndMinutes(long milliseconds) {
        int hours = getHoursFromMilliseconds(milliseconds);
        int minutes = getMinutesFromMilliseconds(milliseconds);

        String hoursAndMinutes = String.format("%02d", hours) + ":";
        hoursAndMinutes += String.format("%02d", minutes);

        return hoursAndMinutes;
    }


    public static Calendar currentDate() {
        return Calendar.getInstance();
    }

    public static Calendar dateMonthAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return calendar;
    }

    public static String getStringDate(Calendar calendar) {
        String result = calendar.get(Calendar.YEAR) + "-";
        result += String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-";
        result += String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
        return result;
    }

    public static List<Date> workingHours() {
        Calendar toDay = Calendar.getInstance();
        toDay.set(Calendar.AM_PM, 0);
        toDay.set(Calendar.HOUR, BookingUtil.BOOKING_START_HOUR);
        toDay.set(Calendar.MINUTE, BookingUtil.BOOKING_START_MINUTE);
        toDay.set(Calendar.SECOND, BookingUtil.BOOKING_START_SECOND);
        Date startTime = toDay.getTime();
        toDay.set(Calendar.HOUR, BookingUtil.BOOKING_END_HOUR);
        toDay.set(Calendar.MINUTE, BookingUtil.BOOKING_END_MINUTE);
        toDay.set(Calendar.SECOND, BookingUtil.BOOKING_END_SECOND);
        Date endTime = toDay.getTime();
        List<Date> list = new ArrayList<>();
        list.add(startTime);
        list.add(endTime);
        return list;
    }

    public static List<Date> workingHours(Date date) {
        Calendar toDay = Calendar.getInstance();
        toDay.setTime(date);
        toDay.set(Calendar.AM_PM, 0);
        toDay.set(Calendar.HOUR, BookingUtil.BOOKING_START_HOUR);
        toDay.set(Calendar.MINUTE, BookingUtil.BOOKING_START_MINUTE);
        toDay.set(Calendar.SECOND, BookingUtil.BOOKING_START_SECOND);
        Date startTime = toDay.getTime();
        toDay.set(Calendar.HOUR, BookingUtil.BOOKING_END_HOUR);
        toDay.set(Calendar.MINUTE, BookingUtil.BOOKING_END_MINUTE);
        toDay.set(Calendar.SECOND, BookingUtil.BOOKING_END_SECOND);
        Date endTime = toDay.getTime();
        List<Date> list = new ArrayList<>();
        list.add(startTime);
        list.add(endTime);
        return list;
    }

    public static Date setStartTime(Date date) {
        Calendar toDay = Calendar.getInstance();
        toDay.setTime(date);
        toDay.set(Calendar.AM_PM, 0);
        toDay.set(Calendar.HOUR, BookingUtil.BOOKING_START_HOUR);
        toDay.set(Calendar.MINUTE, BookingUtil.BOOKING_START_MINUTE);
        toDay.set(Calendar.SECOND, BookingUtil.BOOKING_START_SECOND);
        return toDay.getTime();
    }

    public static Date setEndTime(Date date) {
        Calendar toDay = Calendar.getInstance();
        toDay.setTime(date);
        toDay.set(Calendar.AM_PM, 0);
        toDay.set(Calendar.HOUR, BookingUtil.BOOKING_END_HOUR);
        toDay.set(Calendar.MINUTE, BookingUtil.BOOKING_END_MINUTE);
        toDay.set(Calendar.SECOND, BookingUtil.BOOKING_END_SECOND);
        return toDay.getTime();
    }

    public static String convertDateToString(Date date) {
        DateFormat df = new SimpleDateFormat(DateConstants.DATE_FORMAT);
        return df.format(date);
    }
}
