package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        CriteriaQuery<Booking> where = query.select(root).where
                (builder.and(
                        builder.lessThan(root.get("bookingStartTime"), dateHi),
                        builder.greaterThan(
                                root.get("bookingEndTime"), dateLo)),
                        builder.equal(root.get("room"), room),
                        builder.or(
                                builder.equal(root.get("bookingState"),
                                        BookingState.BOOKED),
                                builder.equal(root.get("bookingState"),
                                        BookingState.ACTIVE)));

        return entityManager.createQuery(query).getResultList();
    }
}
