package ua.softserveinc.tc.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by Demian on 24.05.2016.
 */
public class TimeConverter
{
    private long milliseconds;

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
}
