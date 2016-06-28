package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.entity.Room;


@Repository("roomDao")
public class RoomDaoImpl extends BaseDaoImpl<Room> implements RoomDao {

    public void saveOrUpdate(Room room) {
        this.getEntityManager().merge(room);
    }
}
