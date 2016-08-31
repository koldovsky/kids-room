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

    Long getMaxRecurrentId();

    List<Booking> getRecurrentBookingsByRecurrentId(Long RecurrentId);

}
