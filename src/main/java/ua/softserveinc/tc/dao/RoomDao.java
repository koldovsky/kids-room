package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.List;

public interface RoomDao extends BaseDao<Room> {

    void saveOrUpdate(Room room);

    List<Room> findByManager(User user);
}
