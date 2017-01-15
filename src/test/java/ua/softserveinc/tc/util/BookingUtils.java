package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Booking;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class BookingUtils {

    public static List<Booking> getListOfBookings() throws ParseException {
        Booking booking1 = new Booking();

        booking1.setIdBook(1L);
        booking1.setChild(ChildsUtils.getListOfChildren().get(0));

        Booking booking2 = new Booking();
        Booking booking3 = new Booking();

        return Arrays.asList(booking1, booking2, booking3);
    }

}
