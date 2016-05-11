package ua.softserveinc.tc.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;

import ua.softserveinc.tc.entity.City;
import ua.softserveinc.tc.entity.ColumnConstants.RoomConst;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import javax.persistence.TypedQuery;

@Repository("roomDao")
public class RoomDaoImpl extends BaseDaoImpl<Room> implements RoomDao {

    @Override
    public Room getRoomByName(String name) {
        TypedQuery<Room> query = getEntityManager().createNamedQuery("", Room.class);
        return query.setParameter(RoomConst.NAME_ROOM, name).getSingleResult();
    }

    @Override
    public List<Room> findByCity(City city) {
        return getEntityManager()
                .createQuery("SELECT r FROM Room r WHERE r.city = ?1", Room.class)
                .setParameter(1, city)
                .getResultList();
    }

    @Override
    public List<Room> findByManager(User user) {
        return getEntityManager()
                .createQuery("SELECT r FROM Room r WHERE r.manager = ?1", Room.class)
                .setParameter(1, user)
                .getResultList();
    }
}
