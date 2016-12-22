package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Booking;

import java.util.Arrays;
import java.util.List;

/**
 * Created by comp on 20.12.2016.
 */
public class BoolkingUtils {

    public static List<Booking> getListOfBoolkings()
    {
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        Booking booking3 = new Booking();

        return Arrays.asList(booking1, booking2, booking3);
    }

}
