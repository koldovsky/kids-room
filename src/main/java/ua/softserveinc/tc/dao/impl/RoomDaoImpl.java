package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository
public class RoomDaoImpl extends BaseDaoImpl<Room> implements RoomDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void saveOrUpdate(Room room) {
        entityManager.merge(room);
    }

    @Override
    public List<Booking> reservedBookings(Date dateLo, Date dateHi, Room room) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        query.select(root).where(
                builder.and(
                    builder.lessThan(root.get("bookingStartTime"), dateHi),
                    builder.greaterThan(root.get("bookingEndTime"), dateLo)
                ),
                builder.equal(root.get("room"), room),
                builder.or(
                        builder.equal(root.get("bookingState"), BookingState.BOOKED),
                        builder.equal(root.get("bookingState"), BookingState.ACTIVE)
                )
        );
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Room> findByIsActiveTrue() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> query = builder.createQuery(Room.class);
        Root<Room> root = query.from(Room.class);

        query.select(root).where(builder.equal(root.get("isActive"), true));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<String> emailManagersByRoom(Long room_id) {
        Query query = entityManager.createQuery("select manager.email from Room room " +
                "inner join room.managers as manager " +
                "where room.id = :room_id  ");
        query.setParameter("room_id", room_id);
        return query.getResultList();
    }
}
