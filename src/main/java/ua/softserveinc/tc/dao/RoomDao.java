package ua.softserveinc.tc.dao;

import java.util.List;

import ua.softserveinc.tc.entity.City;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

public interface RoomDao extends BaseDao<Room> {

    Room getRoomByName(String name);

    List<Room> findByCity(City city);

    List<Room> findByManager(User user);
}
