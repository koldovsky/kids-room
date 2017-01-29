package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.util.BookingsCharacteristics;
import ua.softserveinc.tc.util.TwoTuple;

import java.util.Date;
import java.util.List;

/**
 * DAO for bookings.
 *
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

    /**
     * Set the state to Cancelled, sum and duration to 0 for all booking with given
     * recurrent Id
     *
     * @param recurrentId the given recurrent Id
     * @return the number of entities deleted
     */
    int cancelBookingsByRecurrentId(long recurrentId);

    /**
     * Set the state to Cancelled, sum and duration to 0 for all booking with given
     * bookings Id
     *
     * @param bookingId the given recurrent Id
     * @return the number of entities deleted
     */
    int cancelBookingById(long bookingId);

    /**
     * Get arrays of dates of all reserved bookings for given period of time
     * and room. The first date of array is a start date, and other - is end
     * date. If any of the input parameter is null, then array of length of
     * 0 is returns.
     *
     * @param startDate the given start date
     * @param endDate the given end date
     * @param room the given room
     * @return array of Dates
     */
    List<Date[]> getDatesOfReservedBookings(Date startDate, Date endDate, Room room);

    /**
     * Get arrays of dates of all reserved bookings for given period of time
     * and room. The first date of array is a start date, and other - is end
     * date.
     *
     * @param characteristics the given booking characteristics
     * @return array of Dates
     */
    List<Date[]> getDatesOfReservedBookings(BookingsCharacteristics characteristics);

    List<Booking> getRecurrentBookingsByRecurrentId(Long RecurrentId);

    /**
     * Cancell all active and planned bookings in the room
     * @param room where the bookings will be cancelled
     */
    void cancellActiveAndPlannedBookingsInRoom(Room room);
}
