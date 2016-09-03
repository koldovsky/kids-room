package ua.softserveinc.tc.dao.impl;

import org.hibernate.jpa.internal.EntityManagerImpl;
import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class BookingDaoImpl extends BaseDaoImpl<Booking> implements BookingDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<Booking> getBookingsByUserAndRoom(User user, Room room) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);

        Root<Booking> root = query.from(Booking.class);
        query.select(root).where(builder.equal(root.get(BookingConstants.Entity.USER), user),
                builder.equal(root.get(BookingConstants.Entity.ROOM), room));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, User user, Room room, BookingState... bookingStates) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
        Root<Booking> root = criteria.from(Booking.class);

        List<Predicate> restrictions = new ArrayList<>();
        if (startDate != null && endDate != null) {
            //add one day for including last day in parent report
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            c.add(Calendar.DATE, 1);
            endDate = c.getTime();

            restrictions.add(builder.between(root.get(
                    BookingConstants.Entity.START_TIME), startDate, endDate));
        }

        if (bookingStates.length > 0)
            restrictions.add(root.get(BookingConstants.Entity.STATE).in(Arrays.asList(bookingStates)));
        if (user != null)
            restrictions.add(builder.equal(root.get(BookingConstants.Entity.USER), user));
        if (room != null)
            restrictions.add(builder.equal(root.get(BookingConstants.Entity.ROOM), room));

        criteria.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
        criteria.orderBy(builder.asc(root.get(BookingConstants.Entity.START_TIME)));

        return entityManager.createQuery(criteria).getResultList();
    }

    public Long getMaxRecurrentId() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Booking> r = q.from(Booking.class);
        Expression maxExpression = cb.max(r.get("recurrentId"));

        CriteriaQuery<Long> select = q.select(maxExpression);

        TypedQuery<Long> typedQuery = entityManager.createQuery(select);

        Long result = typedQuery.getSingleResult();
        if (result == null) {
            return 0L;
        }
        return result;
    }

    public List<Booking> getRecurrentBookingsByRecurrentId(Long recurrentId){
        CriteriaQuery<Booking> query = null;
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            query = builder.createQuery(Booking.class);
            Root<Booking> root = query.from(Booking.class);
            query.select(root).where(builder.equal(root.get(BookingConstants.Entity.RECURRENTID), recurrentId)).
                    orderBy(builder.asc(root.get(BookingConstants.Entity.START_TIME)));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return entityManager.createQuery(query).getResultList();
    };
}
