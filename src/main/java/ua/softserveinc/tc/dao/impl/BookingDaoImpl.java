package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.util.BookingsCharacteristics;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.*;

@Repository
public class BookingDaoImpl extends BaseDaoImpl<Booking> implements BookingDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional (readOnly = true)
    public List<Booking> getDuplicateBookings(BookingsCharacteristics characteristics) {
        List<Booking> resultList = Collections.singletonList(new Booking());

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
        Root<Booking> root = criteria.from(Booking.class);

        if(characteristics.isCorrectFotDuplicateCheck()) {
            Predicate commonPredicate =
                    getPredicateWereRoomKidsDates(builder, root, characteristics);

            if(!characteristics.getListOfIdOfRecurrentBookings().isEmpty()) {
                criteria.select(root).where(
                        builder.and(
                                root.get(BookingConstants.Entity.RECURRENT_ID)
                                        .in(characteristics.getListOfIdOfRecurrentBookings()).not(),
                                commonPredicate
                        )
                );
            } else if(!characteristics.getListOfIdOfBookings().isEmpty()) {
                criteria.select(root).where(
                        builder.and(
                                root.get(BookingConstants.Entity.ID_OF_BOOKING)
                                        .in(characteristics.getListOfIdOfBookings()).not(),
                                commonPredicate
                        )
                );
            } else {
                criteria.select(root).where(builder.and(commonPredicate));
            }

            resultList = entityManager.createQuery(criteria).getResultList();
        }
        return resultList;
    }

    @Override
    @Transactional (readOnly = true)
    public List<Booking> getBookings(Date startDate, Date endDate, User user,
                                     Room room, boolean includeLastDay,
                                     BookingState... bookingStates) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
        Root<Booking> root = criteria.from(Booking.class);

        List<Predicate> restrictions = new ArrayList<>();
        if (startDate != null && endDate != null) {
            //add one day for including last day in parent report
            if(includeLastDay) {
                Calendar c = Calendar.getInstance();
                c.setTime(endDate);
                c.add(Calendar.DATE, 1);
                endDate = c.getTime();
            }
            restrictions.add(builder.between(root.get(
                    BookingConstants.Entity.START_TIME), startDate, endDate));
        }

        if (bookingStates.length > 0)
            restrictions.add(root.get(BookingConstants.Entity.STATE)
                    .in(Arrays.asList(bookingStates)));
        if (user != null)
            restrictions.add(builder.equal(
                    root.get(BookingConstants.Entity.USER), user));
        if (room != null)
            restrictions.add(builder.equal(
                    root.get(BookingConstants.Entity.ROOM), room));

        criteria.where(builder.and(
                restrictions.toArray(new Predicate[restrictions.size()])));

        criteria.orderBy(builder.asc(
                root.get(BookingConstants.Entity.START_TIME)));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    @Transactional (readOnly = true)
    public List<Booking> getNotCompletedAndCancelledBookings(Date startDate,
                                                             Date endDate,
                                                             Room room){

        List<Booking> resultBookings = Collections.emptyList();

        if (startDate != null && endDate != null && room != null) {

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
            Root<Booking> root = query.from(Booking.class);

            query.select(root).where(
                    builder.and(
                            builder.lessThan(root.get(
                                    BookingConstants.Entity.START_TIME), endDate),
                            builder.greaterThan(root.get(
                                    BookingConstants.Entity.END_TIME), startDate),
                            builder.equal(root.get(
                                BookingConstants.Entity.ROOM), room),
                            root.get(BookingConstants.Entity.STATE).in(
                                (Object[]) BookingConstants.States.getActiveAndBooked())
                    )
            );

            resultBookings = entityManager.createQuery(query).getResultList();
        }

        return resultBookings;
    }

    @Override
    @Transactional (readOnly = true)
    public long getMaxRecurrentId() {
        Long result;
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Booking> root = criteria.from(Booking.class);

        Expression<Long> maxExpression =
                builder.max(root.get(BookingConstants.Entity.RECURRENT_ID));
        CriteriaQuery<Long> select = criteria.select(maxExpression);
        TypedQuery<Long> typedQuery = entityManager.createQuery(select);
        result = typedQuery.getSingleResult();

        return (result != null) ? result : 0L;
    }

    @Override
    @Transactional (readOnly = true)
    public List<Booking> getRecurrentBookingsByRecurrentId(Long recurrentId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        query.select(root).where(builder.equal(
                root.get(BookingConstants.Entity.RECURRENT_ID), recurrentId)).
                orderBy(builder.asc(
                        root.get(BookingConstants.Entity.START_TIME)));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional(rollbackForClassName = {"Exception"})
    public List<Booking> updateRecurrentBookingsDAO(List<Booking> oldBookings,
                                                    List<Booking> newBookings) {
        oldBookings.forEach(entityManager::merge);
        newBookings.forEach(entityManager::persist);
        return newBookings;
    }

    @Override
    @Transactional
    public List<Booking> persistRecurrentBookings(List<Booking> bookings) {
        List<Booking> resultBookings = new ArrayList<>();
        bookings.forEach(booking -> resultBookings.add(entityManager.merge(booking)));
        return resultBookings;

    }

    /*
     * Returns predicate for given builder and root for not cancelled bookings
     * where room and list of children and start date and end date. The start
     * date should be on the index 0 and end date should be on the index 1 of
     * the given array of Date objects. All parameters should not be null.
     *
     * @param builder the object of CriteriaBuilder
     * @param root the object of Root<Booking>
     * @param listOfChildren the list of objects of Child
     * @param room the given room
     * @param dates the given array that contains start and end dates
     * @return appropriate object of class Predicate
     */
    private Predicate getPredicateWereRoomKidsDates(CriteriaBuilder builder,
                                                    Root<Booking> root,
                                                    BookingsCharacteristics characteristics) {
        return builder.and(
                root.get(BookingConstants.Entity.CHILD)
                        .in(characteristics.getChildrenListOfBookings()),
                builder.lessThan(root.get(BookingConstants.Entity.START_TIME),
                        characteristics.getEndDateOfBookings()),
                builder.greaterThan(root.get(BookingConstants.Entity.END_TIME),
                        characteristics.getStartDateOfBookings()),
                root.get(BookingConstants.Entity.STATE).in(
                        (Object[])BookingConstants.States.getActiveAndBooked())
        );
    }
}
