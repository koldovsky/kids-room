package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Room;


public interface RoomDao extends BaseDao<Room> {

    void saveOrUpdate(Room room);
}
