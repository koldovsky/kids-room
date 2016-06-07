package ua.softserveinc.tc.util;

import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Demian on 24.05.2016.
 */

@Service
public class DateUtilImpl implements DateUtil
{
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat dateAndTimeFormat = new SimpleDateFormat(DateConst.DATE_AND_TIME_FORMAT);

    @Override
    public Date toDate(String date)
    {
        try
        {
            return dateFormat.parse(date);
        }
        catch (ParseException e)
        {
            System.err.println("Wrong format of date. " + e.getMessage());
            return null;
        }
    }

    @Override
    public Date toDateAndTime(String date)
    {
        try
        {
            return dateAndTimeFormat.parse(date);
        }
        catch (ParseException e)
        {
            System.err.println("Wrong format of date. " + e.getMessage());
            return null;
        }
    }

    @Override
    public int getHoursFromMilliseconds(long milliseconds)
    {
        return (int) TimeUnit.MILLISECONDS.toHours(milliseconds);
    }

    @Override
    public int getMinutesFromMilliseconds(long milliseconds)
    {
        int hours = getHoursFromMilliseconds(milliseconds);
        return (int) TimeUnit.MILLISECONDS.toMinutes(milliseconds - TimeUnit.HOURS.toMillis(hours));
    }

    public int getRoundedHours(long milliseconds)
    {
        int hours = getHoursFromMilliseconds(milliseconds);
        int minutes = getMinutesFromMilliseconds(milliseconds);

        // 02:00 hours - 2 hours; 02:01 hours - 3 hours
        if (minutes > 0) hours++;

        return hours;
    }

    @Override
    public String toHoursAndMinutes(long milliseconds)
    {
        int hours = getHoursFromMilliseconds(milliseconds);
        int minutes = getMinutesFromMilliseconds(milliseconds);

        String hoursAndMinutes = String.format("%02d", hours) + ":";
        hoursAndMinutes += String.format("%02d", minutes);

        return hoursAndMinutes;
    }


    @Override
    public Calendar currentDate()
    {
        return Calendar.getInstance();
    }

    @Override
    public Calendar dateMonthAgo()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return calendar;
    }

    @Override
    public String getStringDate(Calendar calendar)
    {
        String result = calendar.get(Calendar.YEAR) + "-";
        result += String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-";
        result += String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
        return result;
    }

    public static List<Date> warkingHours(){
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

    /*public static HashMap<Date, Date> splitIntoRequiredPeriods(String startTime, String endTime, String ){

    }*/
    public static String convertDateToString(Date date){
        DateFormat df = new SimpleDateFormat(DateConst.DATE_FORMAT);
        return df.format(date);
    }
}