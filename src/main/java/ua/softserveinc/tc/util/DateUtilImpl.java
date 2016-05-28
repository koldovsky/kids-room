package ua.softserveinc.tc.util;

import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.entity.Booking;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Demian on 24.05.2016.
 */

@Service
public class DateUtilImpl implements DateUtil
{
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date toDate(String date)
    {
        try
        {
            return dateFormat.parse(date);
        }
        catch (ParseException e)
        {
            System.err.println("Wrong format of date." + e.getMessage());
            return null;
        }
    }

    @Override
    public String toHoursAndMinutes(long milliseconds)
    {
        int hours = (int) TimeUnit.MILLISECONDS.toHours(milliseconds);
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(milliseconds - TimeUnit.HOURS.toMillis(hours));

        String hoursAndMinutes = String.format("%02d", hours) + ":";
        hoursAndMinutes += String.format("%02d", minutes);

        return hoursAndMinutes;
    }

    @Override
    public Date sd(Booking booking, String time) {
        DateFormat dfDate = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
        DateFormat dfDateAndTime = new SimpleDateFormat(DateConst.DATE_AND_TIME_FORMAT);
        String dateString = dfDate.format(booking.getBookingStartTime()) + " " + time;
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dfDateAndTime.parse(dateString));
        }
        catch (ParseException e)
        {

        }
        Date date = calendar.getTime();
        return date;

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
}