package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("roomDao")
public class RoomDaoImpl extends BaseDaoImpl<Room> implements RoomDao {


    @Override
    public void saveOrUpdate(Room room){
         /* When we update room, we need some how to execute old rate's from database. */
        if (room.getId() != null) {
            String hqToDeleteOldRates = "DELETE FROM Rate WHERE room = :room";
            Query queryToDeleteOldRates = getEntityManager().createQuery(hqToDeleteOldRates);
            queryToDeleteOldRates.setParameter("room", room);
            queryToDeleteOldRates.executeUpdate();
        }
        getEntityManager().merge(room);
    }

    @Override
    public List<Room> findByManager(User manager) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> query = builder.createQuery(Room.class);
        Root<Room> root = query.from(Room.class);
        query.select(root).where(builder.equal(root.get("manager"), manager));

        return entityManager.createQuery(query).getResultList();
    }
}
