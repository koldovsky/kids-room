package ua.softserveinc.tc.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima- on 12.05.2016.
 */
public final class DateConstants {

    public static final DateFormat DATE_FORMAT_OBJECT = new SimpleDateFormat(DateConstants.DATE_FORMAT);

    public static final long ONE_MINUTE_MILLIS = 60 * 1000;

    public static final int WEEK_LENGTH = 7;

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"; // Quoted "T" to indicate UTC, no timezone offset

    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_AND_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String TIME_FORMAT = "HH:mm";

    public static final String T_DATE_SPLITTER = "T";

    public static final String COLON_TIME_SPLITTER = ":";

    public static final String DATE_T_TIME_REGEXP = "((((\\d{2}(([02468][048])|([13579][26]))"
            + "\\-((((0?[13578])|(1[02]))\\-((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))"
            + "\\-((0?[1-9])|([1-2][0-9])|(30)))|(0?2\\-((0?[1-9])|([1-2][0-9])))))"
            + "|(\\d{2}(([02468][1235679])|([13579][01345789]))\\-((((0?[13578])|(1[02]))"
            + "\\-((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))"
            + "\\-((0?[1-9])|([1-2][0-9])|(30)))|(0?2\\-((0?[1-9])|(1[0-9])|(2[0-8])))))))"
            + "T((?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)))";

    /**
     * Helps translate the short form of the day of the week to int value
     * that is appropriate for Calendar day of week.
     *
     * created by Sviatoslav Hryb on 25.12.2016
     */
    public final static class DaysOfWeek {

        private static final Map<String, Integer> daysOfWeek = new HashMap<>();

        static {
            daysOfWeek.put("Sun", Calendar.SUNDAY);
            daysOfWeek.put("Mon", Calendar.MONDAY);
            daysOfWeek.put("Tue", Calendar.TUESDAY);
            daysOfWeek.put("Wed", Calendar.WEDNESDAY);
            daysOfWeek.put("Thu", Calendar.THURSDAY);
            daysOfWeek.put("Fri", Calendar.FRIDAY);
            daysOfWeek.put("Sat", Calendar.SATURDAY);
        }

        public static Map<String, Integer> getDaysOfWeek() {
            return new HashMap<String, Integer>(daysOfWeek);
        }
    }

    private DateConstants() {
    }
}
