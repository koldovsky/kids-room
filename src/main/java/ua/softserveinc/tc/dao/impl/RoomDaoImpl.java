package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.entity.Room;

import javax.persistence.Query;

@Deprecated
@Repository("roomDao")
public class RoomDaoImpl extends BaseDaoImpl<Room> implements RoomDao {

}
