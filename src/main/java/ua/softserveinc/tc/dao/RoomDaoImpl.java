package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import ua.softserveinc.tc.entity.City;
import ua.softserveinc.tc.constants.ColumnConstants.RoomConst;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository("roomDao")
public class RoomDaoImpl extends BaseDaoImpl<Room> implements RoomDao {

    @Override
    public Room getRoomByName(String name) {
        TypedQuery<Room> query = getEntityManager().createNamedQuery("", Room.class);
        return query.setParameter(RoomConst.NAME_ROOM, name).getSingleResult();
    }

    @Override
    public List<Room> findByCity(City city) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> query = builder.createQuery(Room.class);
        Root<Room> root = query.from(Room.class);
        query.select(root).where(builder.equal(root.get("city"), city));

        return entityManager.createQuery(query).getResultList();
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
