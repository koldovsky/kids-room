package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.EntityConstants.EventConst;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class RoomServiceImpl extends BaseServiceImpl<Room> implements RoomService{

    @Autowired
    private RoomDao roomDao;

    @Override
    public Room findById(Object id) {
        return roomDao.findById(id);
    }

    @Override
    public Room getRoomByName(String name) {
        return roomDao.getRoomByName(name);
    }

    @Override
    public List<Room> findByCity(String city) {
        return roomDao.findByCity(city);
    }

    @Override
    public Room getRoombyManager(User currentManager) {

        return roomDao.getRoombyManager(currentManager);
    }

    @Override
    public List<Room> findByManger(User manager) {
        return roomDao.findByManager(manager);
    }

    @Override
    public void create(Room room) {
        roomDao.saveOrUpdate(room);
    }

    @Override
    public List<Room> findAll()
    {
        return roomDao.findAll();
    }

    @Override
    public Room update(Room entity) {
        return roomDao.update(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Event> getAllEventsInRoom(Room room){
        EntityManager entityManager = roomDao.getEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        query.select(root).where(builder.equal(root.get(EventConst.ID_ROOM), room.getId()));

        return entityManager
                .createQuery(query)
                .getResultList();
    }
}
