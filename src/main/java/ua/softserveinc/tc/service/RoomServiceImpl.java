package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.constants.ColumnConstants.EventConst;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;

import java.util.List;

@Service
public class RoomServiceImpl extends BaseServiceImpl<Room> implements RoomService{

    @Autowired
    private RoomDao roomDao;

    @Override
    public Room getRoomByName(String name) {
        return roomDao.getRoomByName(name);
    }

    @Override
    public void create(Room room) {
        roomDao.create(room);
    }

    @SuppressWarnings("unchecked")
    public List<Event> getAllEventsInRoom(Room room){
        return roomDao
                .getEntityManager()
                .createQuery("SELECT * FROM " + EventConst.TABLENAME +
                        "WHERE " + EventConst.ID_ROOM + " = " + room.getId() + ";")
                .getResultList();
    }
}
