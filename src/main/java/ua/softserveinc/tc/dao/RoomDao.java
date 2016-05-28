package ua.softserveinc.tc.dao;

import java.util.List;

import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

public interface RoomDao extends BaseDao<Room> {

    void saveOrUpdate(Room room);

    Room getRoomByName(String name);

    Room getRoombyManager(User manager);

    List<Room> findByCity(String city);

    List<Room> findByManager(User user);
}
