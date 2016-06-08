package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;

import java.util.Date;
import java.util.List;

/**
 * Created by TARAS on 30.04.2016.
 */
public interface BookingDao extends BaseDao<Booking> {
    List<Booking> getTodayNotCancelledBookingsByRoom(Date startTime, Date endTime, Room room);
    List<Booking> getBookingsByRoomByDay(Date startTime, Date endTime, Room room);
}
