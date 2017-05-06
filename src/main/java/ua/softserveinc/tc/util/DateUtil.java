package ua.softserveinc.tc.util;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.SECONDS;

public final class DateUtil {
    private static DateFormat dateFormat = new SimpleDateFormat(
            DateConstants.SHORT_DATE_FORMAT, Locale.ENGLISH);
    private static DateFormat dateAndTimeFormat = new SimpleDateFormat(
            DateConstants.DATE_AND_TIME_FORMAT);
    private static DateFormat isoDateFormat = new SimpleDateFormat(
            DateConstants.DATE_FORMAT);
    private static DateFormat simpleTimeFormat = new SimpleDateFormat(
            DateConstants.TIME_FORMAT);


    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

    private static final Calendar WORK_CALENDAR = Calendar.getInstance();

    private DateUtil() {
        // Suppresses default constructor, ensuring non-instantiability.
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate dateToLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime dateToLocalTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();
    }

    public static LocalTime differenceBetweenTwoTimes(LocalTime time1, LocalTime time2) {
        return LocalTime.ofSecondOfDay(SECONDS.between(time1, time2));
    }

    public static LocalTime addTwoTimes(LocalTime time1, LocalTime time2) {
        return LocalTime.ofSecondOfDay(time1.toSecondOfDay() + time2.toSecondOfDay());
    }

    public static Date toDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            LOG.error("Error convert to date", e);
            throw new ResourceNotFoundException(e);
        }
    }

    public static Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDateAndTime(String date) {
        try {
            return dateAndTimeFormat.parse(date);
        } catch (ParseException e) {
            LOG.error("Error convert to date and time", e);
            throw new ResourceNotFoundException(e);
        }
    }

    public static Date toDateISOFormat(String dateToParse) {
        try {
            return isoDateFormat.parse(dateToParse);
        } catch (ParseException e) {
            LOG.error("Error convert to date ISO format", e);
            throw new ResourceNotFoundException(e);
        }
    }

    public static String toIsoString(Date date) {
        return isoDateFormat.format(date);
    }

    public static Date toDate(Calendar calendar) {
        return calendar.getTime();
    }

    public static Calendar dateNow() {
        return Calendar.getInstance();
    }

    public static Calendar dateDayAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar;
    }

    public static Calendar dateTomorrow(){
        Calendar result = Calendar.getInstance();
        result.add(Calendar.DAY_OF_MONTH, 1);
        return result;
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

    private static int getHoursFromMilliseconds(long milliseconds) {
        return (int) TimeUnit.MILLISECONDS.toHours(milliseconds);
    }

    private static int getMinutesFromMilliseconds(long milliseconds) {
        int hours = getHoursFromMilliseconds(milliseconds);
        return (int) TimeUnit.MILLISECONDS.toMinutes(
                milliseconds - TimeUnit.HOURS.toMillis(hours));
    }

    public static int getRoundedHours(long milliseconds) {
        int hours = getHoursFromMilliseconds(milliseconds);
        int minutes = getMinutesFromMilliseconds(milliseconds);

        // 02:10 hours - 2 hours; 02:11 hours - 3 hours
        if (minutes > 10) {
            hours++;
        }
        return hours;
    }

    public static String toHoursAndMinutes(long milliseconds) {
        int hours = getHoursFromMilliseconds(milliseconds);
        int minutes = getMinutesFromMilliseconds(milliseconds);

        String hoursAndMinutes = String.format("%02d", hours) + ":";
        hoursAndMinutes += String.format("%02d", minutes);

        return hoursAndMinutes;
    }

    public static Date setTime(String time) {
        Calendar day = Calendar.getInstance();
        String toDay = getStringDate(day) + " " + time;
        try {
            return dateAndTimeFormat.parse(toDay);
        } catch (ParseException e) {
            LOG.error("Error. Set time", e);
            throw new ResourceNotFoundException(e);
        }
    }

    public static String convertDateToString(Date date) {
        DateFormat df = new SimpleDateFormat(DateConstants.DATE_FORMAT);
        return df.format(date);
    }

    /**
     * Transforms the given string to the Date object and set the begin
     * of the day to the given date. The given date must be in either of
     * two formats: YYY-MM-DD'T'HH:mm:ss or YYY-MM-DD
     *
     * @param date the given date
     * @return the resulted day object
     */
    public static Date toBeginOfDayDate(String date) {

        return toDateISOFormat(
                getStringDatePartOfDateString(date)
                        + DateConstants.T_DATE_SPLITTER
                        + DateConstants.BEGIN_OF_THE_DAY_TIME);
    }

    /**
     * Transforms the given string to the Date object and set the end
     * of the day to the given date. The given date must be in either of
     * two formats: YYY-MM-DD'T'HH:mm:ss or YYY-MM-DD
     *
     * @param date the given date
     * @return the resulted day object
     */
    public static Date toEndOfDayDate(String date) {

        return toDateISOFormat(
                getStringDatePartOfDateString(date)
                + DateConstants.T_DATE_SPLITTER
                + DateConstants.END_OF_THE_DAY_TIME);
    }

    /**
     * Translate the short form of the day of the week to int value
     * that is appropriate for Calendar day of week.
     *
     * @param day the given short form of the day
     * @return translated int value
     */
    public static Integer getDayOfWeek(String day) {

        return DateConstants.DaysOfWeek.getDaysOfWeek().get(day);
    }

    /**
     * Translate the array of short forms of the days of the week to
     * array of int values that is appropriate for Calendar days of week.
     *
     * @param days the given array of short forms of the days
     * @return translated array of int values
     */
    public static int[] getIntDaysOfWeek(String[] days) {
        int[] result = new int[days.length];

        for(int i = 0; i < days.length; i++) {
            Integer dayOfWeek = getDayOfWeek(days[i]);
            if(dayOfWeek == null) {
                result = new int[0];
                break;
            }
            result[i] = dayOfWeek;
        }
        return result;
    }

    /**
     * Returns the day of the year from given Date object.
     *
     * @param date the given date object
     * @return the day of the yehar
     */
    public static int getDayFromDate(Date date) {
        WORK_CALENDAR.setTime(date);

        return WORK_CALENDAR.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Returns tha year from given Date object
     *
     * @param date the given date object
     * @return the day of the year
     */
    public static int getYearFromDate(Date date) {
        WORK_CALENDAR.setTime(date);

        return WORK_CALENDAR.get(Calendar.YEAR);
    }

    /*
     * Gets the string part of the date that is represented String object.
     * If the given date is not in the format YYY-MM-DD'T'HH:mm:ss than
     * this date will be returned.
     *
     * @param date the given date
     * @return the date part of the date
     */
    private static String getStringDatePartOfDateString(String date) {

        return Pattern.matches(DateConstants.DATE_T_TIME_REGEXP, date) ?
            date.substring(0, DateConstants.T_POSITION_IN_DATE_STRING) : date;
    }
}
