package ua.softserveinc.tc.constants;

public class ExcelConstants {

    private ExcelConstants() {
    }

    public static final class Headers {
        public static final String PARENT = "Parent";
        public static final String EMAIL = "Email";
        public static final String ABONNEMENT = "Abonnement time";
        public static final String BOOKING_TIME = "Overal booking time";
        public static final String SUM = "Sum";

        public static final String BOOKING_DATE = "Date";
        public static final String KID = "Kid";
        public static final String PLACE = "Place";
        public static final String BOOKING_START_TIME = "Start time";
        public static final String BOOKING_END_TIME = "End time";
        public static final String BOOKING_DURATION = "Duration";
        public static final String DISCOUNT = "Discount";

    }

    public static final class Fields {
        public static final String TOTAL_SUM = "TOTAL SUM: ";
        public static final String CURRENCY = " UAH";
        public static final String ROOM = "ROOM: ";
        public static final String PARENT = "PARENT: ";
    }

    public static final class Other {
        public static final String NOT_PROVIDED = "not provided";
        public static final String FILE_NAME = "fileName";
        public static final String EXCEL_DATA = "data";
        public static final String SHEET_NAME = "Report";
    }
}
