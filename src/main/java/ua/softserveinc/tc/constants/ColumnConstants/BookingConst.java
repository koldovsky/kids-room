package ua.softserveinc.tc.constants.ColumnConstants;

/**
 * Created by TARAS on 05.05.2016.
 */
public interface BookingConst {

    String TABLE_NAME_BOOKING = "bookings";

    String ID_BOOK = "id_book";

    String BOOKING_START_TIME = "booking_start_time";

    String BOOKING_END_TIME = "booking_end_time";

    String COMMENT = "comment";

    String IS_CANCELED = "is_canceled";

    // Don't need ID_MANAGER & ID_CITY.
    // Because we will get this constants from appropriate tables from relationship.

    //String ID_CHILD = "id_child";
    //String ID_ROOM = "id_room";
    //String ID_USER = "id_user";

}
