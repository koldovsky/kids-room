package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.util.BookingsCharacteristics;

import java.util.List;

/**
 * DAO for bookings.
 *
 * Rewritten by Sviatoslav Hryb on 05.10.2017
 */
public interface BookingDao extends BaseDao<Booking> {

    /**
     * Gets all bookings that match the given room, list of children, start date,
     * and end date and not match any of the booking id from the given list of Ids.
     * The given booking id can be equals to any long value with no restriction.
     * Start date should be on the index 0 and end date should be on the index 1 of
     * the given array of dates. The given parameters should not be null and each values
     * in the lists should not be null, and list of rooms and children should not be empty,
     * and arrays of dates should not contain null.Otherwise the empty list will be returned.
     *
     * @param characteristics the object that characterize the bookings
     * @return the list of resulting bookings
     */
    List<Booking> getDuplicateBookings(BookingsCharacteristics characteristics);

    /**
     * Returns the max existed recurrent booking id. If no recurrent id exists
     * than 0 will be returned.
     *
     * @return the max recurrent booking id
     */
    long getMaxRecurrentId();

    /**
     * Persists list of bookings and return corresponding list. The given list
     * should not be null.
     *
     * @param bookings the list of bookings for persisting
     * @return the list of persisted bookings
     */
    List<Booking> persistRecurrentBookings(List<Booking> bookings);

    /**
     * Gets the all bookings for given booking characteristics.
     *
     * @param characteristics the given characteristics
     * @return the list of appropriate bookings
     */
    List<Booking> getBookings(BookingsCharacteristics characteristics);

    List<Booking> getRecurrentBookingsByRecurrentId(Long RecurrentId);

    List<Booking> updateRecurrentBookingsDAO(List<Booking> oldBookings,
                                             List<Booking> newBookings);

    /**
     * Cancell all active and planned bookings in the room
     * @param room where the bookings will be cancelled
     */
    void cancellActiveAndPlannedBookingsInRoom(Room room);
}
