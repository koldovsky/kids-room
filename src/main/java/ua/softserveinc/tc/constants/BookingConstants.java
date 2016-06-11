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
                BookingState.CALCULATE_SUM,
                BookingState.COMPLETED
        };

        BookingState[] ACTIVE_AND_BOOKED = {
                BookingState.ACTIVE,
                BookingState.BOOKED
        };
    }

    interface Entity {

        String SUM = "sum";

        String USER = "user";

        String ROOM = "room";

        String STATE = "bookingState";

        String START_TIME = "bookingStartTime";

    }

    interface Model {

        String MANAGER_CONF_BOOKING_VIEW = "manager-confirm-booking";

        String MANAGER_EDIT_BOOKING_VIEW = "manager-edit-booking";

        String LIST_BOOKINGS = "listBooking";

        String CANCEL_BOOKING = "cancelBook/{idBooking}";

        String SET_START_TIME = "/setTime";

        String SET_END_TIME = "/setEndTime";

        String BOOK_DURATION = "/BookDuration";

        String LIST_BOOKING = "/listBook";
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
