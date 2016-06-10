package ua.softserveinc.tc.constants;

import ua.softserveinc.tc.entity.BookingState;

/**
 * Created by Петришак on 03.06.2016.
 */
public interface BookingConstants {

    interface States {
        BookingState[] NOT_CANCELLED = {
                BookingState.ACTIVE,
                BookingState.BOOKED,
                BookingState.COMPLETED
        };

        BookingState[] ACTIVE_AND_BOOKED = {
                BookingState.ACTIVE,
                BookingState.BOOKED
        };
    }

    interface Entity {

        public static final String SUM = "sum";

        public static final String USER = "user";

        public static final String ROOM = "room";

        public static final String STATE = "bookingState";

        public static final String START_TIME = "bookingStartTime";

    }

    interface Model {

        public static final String MANAGER_CONF_BOOKING_VIEW = "manager-confirm-booking";

        public static final String MANAGER_EDIT_BOOKING_VIEW = "manager-edit-booking";

        public static final String LIST_BOOKINGS = "listBooking";

        public static final String CANCEL_BOOKING = "cancelBook/{idBooking}";

        public static final String SET_START_TIME = "/setTime";

        public static final String SET_END_TIME = "/setEndTime";

        public static final String BOOK_DURATION = "/BookDuration";

        public static final String LIST_BOOKING = "/listBook";
    }

    interface DB {

        String TABLE_NAME_BOOKING = "bookings";

        String ID_BOOK = "id_book";

        String BOOKING_START_TIME = "booking_start_time";

        String BOOKING_END_TIME = "booking_end_time";

        String COMMENT = "comment";

        String DURATION = "duration";

        String SUM = "sum";

        String BOOKING_STATE = "booking_state";
    }
}
