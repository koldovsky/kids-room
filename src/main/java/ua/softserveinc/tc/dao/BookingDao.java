package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.util.BookingsCharacteristics;

import java.util.Date;
import java.util.List;

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
     * Return list all of the bookings that have booking states BookingState.BOOKED
     * and BookingState.Active. If any of the parameters is a null the method
     * will returns empty list.
     *
     * @param startDate the start date of period
     * @param endDate the end date of period
     * @param room the room of bookings
     * @return list of bookings
     */
    List<Booking> getNotCompletedAndCancelledBookings(Date startDate, Date endDate, Room room);

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

    List<Booking> getBookings(Date startDate, Date endDate, User user, Room room,
                              boolean includeLastDay, BookingState... bookingStates);

    List<Booking> getRecurrentBookingsByRecurrentId(Long RecurrentId);

    List<Booking> updateRecurrentBookingsDAO(List<Booking> oldBookings,
                                             List<Booking> newBookings);
}
