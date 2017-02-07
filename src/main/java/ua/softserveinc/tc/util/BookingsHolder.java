package ua.softserveinc.tc.util;


import ua.softserveinc.tc.dto.BookingDto;

import java.util.List;

/**
 * A tuple for hold list of BookingsDto and Error message.
 */
public class BookingsHolder extends TwoTuple<List<BookingDto>,String> {

    /**
     * To create object of this class we must use this constructor.
     *
     * @param bookings list of bookings dto objects
     * @param errorCode string error code of message
     */
    public BookingsHolder (List<BookingDto> bookings, String errorCode) {

        super(bookings, errorCode);
    }

    /**
     * Returns list of bookings
     *
     * @return Returns list of bookings
     */
    public List<BookingDto> getBookings() {

        return getFirst();
    }

    /**
     * Returns string error code of message
     *
     * @return string error code of message
     */
    public String getErrorCode() {

        return getSecond();
    }

}
