package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.List;

/**
 * Created by TARAS on 30.04.2016.
 */
public interface BookingDao extends BaseDao<Booking> {
    List<Booking> getBookingsByUserAndRoom(User user, Room room);
}
