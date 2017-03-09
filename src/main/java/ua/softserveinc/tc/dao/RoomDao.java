package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;

import java.util.Date;
import java.util.List;

public interface RoomDao extends BaseDao<Room> {
    void saveOrUpdate(Room room);

    List<Booking> reservedBookings(Date dateLo, Date dateHi, Room room);

    List<Room> findByIsActiveTrue();

    List<String> emailManagersByRoom(Long room_id);
}
