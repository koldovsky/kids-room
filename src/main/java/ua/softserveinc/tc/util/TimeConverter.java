package ua.softserveinc.tc.util;

import org.springframework.beans.factory.annotation.Autowired;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.service.BookingService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Demian on 24.05.2016.
 */
public class TimeConverter
{

    private long milliseconds;

    public static DateFormat dayformat = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);

    public long getMilliseconds()
    {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds)
    {
        this.milliseconds = milliseconds;
    }

    public TimeConverter(long milliseconds)
    {
        this.milliseconds = milliseconds;
    }

    public String toHoursAndMinutes()
    {
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);

        String hoursAndMinutes = String.format("%02d", hours) + ":";
        hoursAndMinutes += String.format("%02d", minutes);

        return hoursAndMinutes;
    }

    public Date sd(Booking booking, String time) throws ParseException {
        DateFormat dfDate = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
        DateFormat dfDateAndTime = new SimpleDateFormat(DateConst.DATE_AND_TIME_FORMAT);
        String dateString = dfDate.format(booking.getBookingStartTime()) + " " + time;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dfDateAndTime.parse(dateString));
        Date date = calendar.getTime();
        return date;

    }


}
