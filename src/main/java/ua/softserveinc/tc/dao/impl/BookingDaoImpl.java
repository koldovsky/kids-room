package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by TARAS on 01.05.2016.
 */
@Repository
public class BookingDaoImpl extends BaseDaoImpl<Booking> implements BookingDao {

    @Override
    public List<Booking> getTodayNotCancelledBookingsByRoom(Date startTime, Date endTime, Room room ) {

        EntityManager entityManager = getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

            query.select(root).where(builder.and(builder.between(root.get(BookingConstants.Entity.START_TIME),
                            startTime, endTime)), builder.notEqual(root.get(BookingConstants.Entity.STATE),
                            BookingConstants.DB.BOOKING_CENCELLD),
                            builder.equal(root.get(BookingConstants.Entity.ROOM), room));
            query.orderBy(builder.asc(root.get(BookingConstants.Entity.START_TIME)));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Booking> getTodayBookingsByRoom(Date startTime, Date endTime, Room room) {

        EntityManager entityManager = getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        query.select(root).where(builder.and(builder.between(root.get(BookingConstants.Entity.START_TIME),
                startTime, endTime)), builder.equal(root.get(BookingConstants.Entity.STATE),
                BookingConstants.DB.BOOKING_BOOKED),
                builder.equal(root.get(BookingConstants.Entity.ROOM), room));
        query.orderBy(builder.asc(root.get(BookingConstants.Entity.START_TIME)));

        return entityManager.createQuery(query).getResultList();
    }
}
