package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Date;
import java.util.List;

public interface BookingDao extends BaseDao<Booking> {
    List<Booking> getBookingsByUserAndRoom(User user, Room room);

    List<Booking> getBookings(Date startDate, Date endDate, User user, Room room, BookingState... bookingStates);

    /**
     * Return list all of the bookings that have booking states BookingState.BOOKED
     * and BookingState.Active. If any of the parameter is null the method will return
     * empty list.
     */
    List<Booking> getNotCompletedAndCancelledBookings(Date startDate, Date endDate, Room room);

    Long getMaxRecurrentId();

    List<Booking> getRecurrentBookingsByRecurrentId(Long RecurrentId);

    List<Booking> updateRecurrentBookingsDAO(List<Booking> oldBookings, List<Booking> newBookings);

}
