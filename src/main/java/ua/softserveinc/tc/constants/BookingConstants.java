package ua.softserveinc.tc.constants;

import ua.softserveinc.tc.entity.BookingState;

/**
 * Created by Петришак on 03.06.2016.
 */
public final class BookingConstants {

    private BookingConstants() {
    }

    public static final class States {

        private static final BookingState[] NOT_CANCELLED = {
                BookingState.ACTIVE,
                BookingState.BOOKED,
                BookingState.CALCULATE_SUM,
                BookingState.COMPLETED
        };

        private static final BookingState[] ACTIVE_AND_BOOKED = {
                BookingState.ACTIVE,
                BookingState.BOOKED
        };

        private States(){
        }

        public static BookingState[] getNotCancelled(){
            return NOT_CANCELLED;
        }

        public static BookingState[] getActiveAndBooked(){
            return ACTIVE_AND_BOOKED;
        }

    }

    public static final class Entity {

        public static final String SUM = "sum";

        public static final String USER = "user";

        public static final String ROOM = "room";

        public static final String STATE = "bookingState";

        public static final String START_TIME = "bookingStartTime";

        public static final String RECURRENTID = "recurrentId";

        private Entity(){}
    }

    public static final class Model {

        public static final String MANAGER_CONF_BOOKING_VIEW = "manager-confirm-booking";

        public static final String MANAGER_EDIT_BOOKING_VIEW = "manager-edit-booking";

        public static final String LIST_BOOKINGS = "listBooking";

        public static final String CANCEL_BOOKING = "cancelBook/{idBooking}";

        public static final String SET_START_TIME = "/setTime";

        public static final String SET_END_TIME = "/setEndTime";

        public static final String BOOK_DURATION = "/BookDuration";

        public static final String LIST_BOOKING = "/listBook";

        private Model() {}
    }

    public static final class DB {

        public static final String TABLE_NAME_BOOKING = "bookings";

        public static final String ID_BOOK = "id_book";

        public static final String BOOKING_START_TIME = "booking_start_time";

        public static final String BOOKING_END_TIME = "booking_end_time";

        public static final String COMMENT = "comment";

        public static final String DURATION = "duration";

        public static final String SUM = "sum";

        public static final String BOOKING_STATE = "booking_state";

        public static final String ID_RECURRENT = "id_recurrent";
        private DB(){}
    }

    public static final class View {

        public static final String MAIN_PAGE = "index";

        private View(){}
    }
}
