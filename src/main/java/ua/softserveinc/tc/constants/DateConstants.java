package ua.softserveinc.tc.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.Calendar;

public final class DateConstants {

    public static final DateFormat DATE_FORMAT_OBJECT =
            new SimpleDateFormat(DateConstants.DATE_FORMAT);

    public static final long ONE_MINUTE_MILLIS = 60 * 1000;

    public static final int WEEK_LENGTH = 7;

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"; // Quoted "T" to indicate UTC, no timezone offset

    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_AND_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String TIME_FORMAT = "HH:mm";

    public static final String BEGIN_OF_THE_DAY_TIME = "00:00:00";

    public static final String END_OF_THE_DAY_TIME = "23:59:59";

    public static final int T_POSITION_IN_DATE_STRING = 10;

    public static final String T_DATE_SPLITTER = "T";

    public static final String COLON_TIME_SPLITTER = ":";

    public static final String DATE_T_TIME_REGEXP = "((((\\d{2}(([02468][048])|([13579][26]))"
            + "\\-((((0[13578])|(1[02]))\\-((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))"
            + "\\-((0[1-9])|([1-2][0-9])|(30)))|(02\\-((0[1-9])|([1-2][0-9])))))"
            + "|(\\d{2}(([02468][1235679])|([13579][01345789]))\\-((((0[13578])|(1[02]))"
            + "\\-((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))"
            + "\\-((0[1-9])|([1-2][0-9])|(30)))|(02\\-((0[1-9])|(1[0-9])|(2[0-8])))))))"
            + "T((?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)))";

    public static final String DATE_REGEXP = "((((\\d{2}(([02468][048])|([13579][26]))"
            + "\\-((((0[13578])|(1[02]))\\-((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))"
            + "\\-((0[1-9])|([1-2][0-9])|(30)))|(02\\-((0[1-9])|([1-2][0-9])))))"
            + "|(\\d{2}(([02468][1235679])|([13579][01345789]))\\-((((0[13578])|(1[02]))"
            + "\\-((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))"
            + "\\-((0[1-9])|([1-2][0-9])|(30)))|(02\\-((0[1-9])|(1[0-9])|(2[0-8]))))))))";

    public static final String DATE_T_SPACE_TIME_REGEXP = "((((\\d{2}(([02468][048])|([13579][26]))"
            + "\\-((((0[13578])|(1[02]))\\-((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))"
            + "\\-((0[1-9])|([1-2][0-9])|(30)))|(02\\-((0[1-9])|([1-2][0-9])))))"
            + "|(\\d{2}(([02468][1235679])|([13579][01345789]))\\-((((0[13578])|(1[02]))"
            + "\\-((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))"
            + "\\-((0[1-9])|([1-2][0-9])|(30)))|(02\\-((0[1-9])|(1[0-9])|(2[0-8])))))))"
            + "[T\u0020]((?:[01]\\d|2[0123]):(?:[012345]\\d)(:(?:[012345]\\d))?))";

    public static final String TIME_REGEX = "((?:[01]\\d|2[0123]):(?:[012345]\\d)(:(?:[012345]\\d))?)";

    public static final String TWENTY_FOUR_HOURS_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]";


    /**
     * Helps translate the short form of the day of the week to int value
     * that is appropriate for Calendar day of week.
     *
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

            return new HashMap<>(daysOfWeek);
        }
    }

    public final static class ListDaysOfWeek {

        private static final List<String> listDaysOfWeek = new ArrayList<>();

        static {
            listDaysOfWeek.add("Sun");
            listDaysOfWeek.add("Mon");
            listDaysOfWeek.add("Tue");
            listDaysOfWeek.add("Wed");
            listDaysOfWeek.add("Thu");
            listDaysOfWeek.add("Fri");
            listDaysOfWeek.add("Sat");
        }

        public static List<String> getListDaysOfWeek() {

            return new ArrayList<>(listDaysOfWeek);
        }
    }

    public final static class EventColors {

        private static final List<String> eventColors = new ArrayList<>();

        static {
            eventColors.add("#eb6f63");
            eventColors.add("#ffcd5c");
            eventColors.add("#9b3aa1");
            eventColors.add("#044d92");
            eventColors.add("#1ba1e2");
            eventColors.add("#636363");
            eventColors.add("#51d466");
            eventColors.add("#84fff7");
            eventColors.add("#d3af37");
            eventColors.add("#f98e2e");
        }

        public static List<String> getEventColors() {

            return new ArrayList<>(eventColors);
        }
    }


    private DateConstants() {
    }
}
