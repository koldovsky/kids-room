package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.entity.Room;

import javax.persistence.Query;

@Repository("roomDao")
public class RoomDaoImpl extends BaseDaoImpl<Room> implements RoomDao {

    @Override
    public void saveOrUpdate(Room room) {
         /* When we update room, we need some how to execute old rate's from database. */
        if (room.getId() != null) {
            String hqToDeleteOldRates = "DELETE FROM Rate WHERE room = :room";
            Query queryToDeleteOldRates = getEntityManager().createQuery(hqToDeleteOldRates);
            queryToDeleteOldRates.setParameter("room", room);
            queryToDeleteOldRates.executeUpdate();
        }
        getEntityManager().merge(room);
    }
}
