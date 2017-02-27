package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.constants.SQLConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.util.BookingsCharacteristics;
import ua.softserveinc.tc.util.TwoTuple;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import java.util.Calendar;


@Repository
public class BookingDaoImpl extends BaseDaoImpl<Booking> implements BookingDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Booking> getDuplicateBookings(BookingsCharacteristics characteristics) {
        List<Booking> resultList = Collections.singletonList(new Booking());

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
        Root<Booking> root = criteria.from(Booking.class);

        if (characteristics.isCorrectFotDuplicateCheck()) {

            List<Predicate> restrictions = new ArrayList<>();

            // Excluding recurrent or booking id because they must not be counted while updating
            if (characteristics.hasSetRecurrentIdsOfBookings()) {
                restrictions.add(builder.or(
                        root.get(BookingConstants.Entity.RECURRENT_ID)
                                .isNull(),
                        root.get(BookingConstants.Entity.RECURRENT_ID)
                                .in(characteristics.getRecurrentIdsOfBookings()).not()
                ));

            } else if (characteristics.hasSetIdsOfBookings()) {
                restrictions.add(root.get(BookingConstants.Entity.ID_OF_BOOKING)
                        .in(characteristics.getIdsOfBookings()).not());

            }

            //Include all bookings that is in given time range and have booked or active status
            restrictions.addAll(Arrays.asList(
                    root.get(BookingConstants.Entity.CHILD)
                            .in(characteristics.getChildren()),
                    builder.lessThan(root.get(BookingConstants.Entity.START_TIME),
                            characteristics.getEndDateOfBookings()),
                    builder.greaterThan(root.get(BookingConstants.Entity.END_TIME),
                            characteristics.getStartDateOfBookings()),
                    root.get(BookingConstants.Entity.STATE).in(
                            (Object[]) BookingConstants.States.getActiveAndBooked())
            ));

            criteria.select(root).where(builder.and(
                    restrictions.toArray(new Predicate[restrictions.size()])));


            resultList = entityManager.createQuery(criteria).getResultList();
        }

        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookings(BookingsCharacteristics characteristics) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
        Root<Booking> root = criteria.from(Booking.class);

        List<Predicate> restrictions = new ArrayList<>();
        if (characteristics.hasSetDates()) {
            restrictions.add(builder.and(
                    builder.lessThan(root.get(BookingConstants.Entity.START_TIME),
                            characteristics.getEndDateOfBookings()),
                    builder.greaterThan(root.get(BookingConstants.Entity.END_TIME),
                            characteristics.getStartDateOfBookings())
            ));
        }

        if (characteristics.hasSetOnlyStartDate()) {
            restrictions.add(builder.greaterThanOrEqualTo(root.get(
                    BookingConstants.Entity.START_TIME), characteristics.getStartDateOfBookings()));
        }

        if (characteristics.hasSetOnlyEndDate()) {
            restrictions.add(builder.lessThanOrEqualTo(root.get(BookingConstants.Entity.END_TIME),
                    characteristics.getEndDateOfBookings()));
        }

        if (characteristics.hasSetBookingsStates()) {
            restrictions.add(root.get(BookingConstants.Entity.STATE)
                    .in(characteristics.getBookingsStates()));
        }
        if (characteristics.hasSetUsers()) {
            restrictions.add(root.get(BookingConstants.Entity.USER)
                    .in(characteristics.getUsers()));
        }
        if (characteristics.hasSetRooms()) {
            restrictions.add(root.get(BookingConstants.Entity.ROOM)
                    .in(characteristics.getRooms()));
        }
        if (characteristics.hasSetChildren()) {
            restrictions.add(root.get(BookingConstants.Entity.CHILD)
                    .in(characteristics.getChildren()));
        }

        criteria.select(root).where(builder.and(
                restrictions.toArray(new Predicate[restrictions.size()])));

        criteria.orderBy(builder.asc(
                root.get(BookingConstants.Entity.START_TIME)));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<Date[]> getDatesOfReservedBookings(Date startDate, Date endDate, Room room) {

        return getDatesOfReservedBookings(
                new BookingsCharacteristics.Builder()
                        .setDates(new Date[] {startDate, endDate})
                        .setRooms(Collections.singletonList(room))
                        .build()
        );
    }

    @Override
    public List<Date[]> getDatesOfReservedBookings(BookingsCharacteristics characteristics) {
        Date startDate = characteristics.getStartDateOfBookings();
        Date endDate = characteristics.getEndDateOfBookings();
        Room room = characteristics.getRooms().get(0);
        Calendar datesCalendar = Calendar.getInstance();
        datesCalendar.setTime(startDate);
        int startHour =  datesCalendar.get(Calendar.HOUR_OF_DAY);
        int startMinute =  datesCalendar.get(Calendar.MINUTE);
        datesCalendar.setTime(endDate);
        int endHour =  datesCalendar.get(Calendar.HOUR_OF_DAY);
        int endMinute =  datesCalendar.get(Calendar.MINUTE);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Date[]> criteria = builder.createQuery(Date[].class);
        Root<Booking> root = criteria.from(Booking.class);

        List<Predicate> restrictions = new ArrayList<>();

        // Excluding recurrent or booking id because they must not be counted while updating
        if (characteristics.hasSetRecurrentIdsOfBookings()) {
            restrictions.add(builder.or(
                    root.get(BookingConstants.Entity.RECURRENT_ID)
                            .isNull(),
                    root.get(BookingConstants.Entity.RECURRENT_ID)
                            .in(characteristics.getRecurrentIdsOfBookings()).not()
            ));

        } else if (characteristics.hasSetIdsOfBookings()) {
            restrictions.add(root.get(BookingConstants.Entity.ID_OF_BOOKING)
                    .in(characteristics.getIdsOfBookings()).not());

        }

        //Include all bookings that is in given time range and have booked or active status
        restrictions.addAll(Arrays.asList(
                builder.lessThan(root.get(BookingConstants.Entity.START_TIME), endDate),
                builder.greaterThan(root.get(BookingConstants.Entity.END_TIME), startDate),
                builder.equal(root.get(BookingConstants.Entity.ROOM), room),
                builder.or(
                        builder.lessThan(
                                builder.function(SQLConstants.HOUR_FUNCTION, Integer.class,
                                        root.get(BookingConstants.Entity.START_TIME)),
                                endHour
                        ),
                        builder.and(builder.equal(
                                builder.function(SQLConstants.HOUR_FUNCTION, Integer.class,
                                        root.get(BookingConstants.Entity.START_TIME)),
                                endHour),
                                builder.lessThan(
                                        builder.function(SQLConstants.MINUTE_FUNCTION,
                                                Integer.class,
                                                root.get(BookingConstants.Entity.START_TIME)
                                        ),
                                        endMinute
                                )
                        )
                ),
                builder.or(
                        builder.greaterThan(
                                builder.function(SQLConstants.HOUR_FUNCTION, Integer.class,
                                        root.get(BookingConstants.Entity.END_TIME)),
                                startHour
                        ),
                        builder.and(builder.equal(
                                builder.function(SQLConstants.HOUR_FUNCTION, Integer.class,
                                        root.get(BookingConstants.Entity.END_TIME)),
                                startHour),
                                builder.greaterThan(
                                        builder.function(SQLConstants.MINUTE_FUNCTION,
                                                Integer.class,
                                                root.get(BookingConstants.Entity.END_TIME)
                                        ),
                                        startMinute
                                )
                        )
                ),
                root.get(BookingConstants.Entity.STATE).in(
                        (Object[]) BookingConstants.States.getActiveAndBooked()
                )
        ));

        criteria.multiselect(
                root.get(BookingConstants.Entity.START_TIME),
                root.get(BookingConstants.Entity.END_TIME))
                .where(builder.and(
                        restrictions.toArray(new Predicate[restrictions.size()])
                ))
                .orderBy(builder.asc(root.get(BookingConstants.Entity.START_TIME)));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public int cancelBookingsByRecurrentId(long recurrentId) {

        return cancelBookingsWherePathAndValue(BookingConstants.Entity.RECURRENT_ID, recurrentId);
    }

    @Override
    public int cancelBookingById(long bookingId) {

        return cancelBookingsWherePathAndValue(BookingConstants.Entity.ID_OF_BOOKING, bookingId);
    }

    @Override
    public long getMaxRecurrentId() {
        Long result;
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Booking> root = criteria.from(Booking.class);

        criteria.select(builder.max(root.get(BookingConstants.Entity.RECURRENT_ID)));

        result = entityManager.createQuery(criteria).getSingleResult();

        return (result != null) ? result : 0L;
    }

    @Override
    public List<Booking> getRecurrentBookingsByRecurrentId(Long recurrentId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
        Root<Booking> root = criteria.from(Booking.class);

        criteria.select(root).where(builder.equal(
                root.get(BookingConstants.Entity.RECURRENT_ID), recurrentId))
                    .orderBy(builder.asc(
                        root.get(BookingConstants.Entity.START_TIME)));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public Long countByRoomAndBookingState(Room room, BookingState bookingState) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        query.select(root).where(
                builder.and(
                        builder.equal(root.get(BookingConstants.Entity.ROOM), room),
                        builder.equal(root.get(BookingConstants.Entity.STATE), bookingState)
                )
        );

        int result = entityManager.createQuery(query).getResultList().size();

        return (result == 0) ? 0L : result;
    }

    @Override
    public List<Booking> findByBookingState(BookingState bookingState) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        query.select(root).where(builder.equal(root.get(BookingConstants.Entity.STATE), bookingState));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Booking> findByBookingStateAndBookingStartTimeLessThan(BookingState bookingState, Date start) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        query.select(root).where(
                builder.and(
                        builder.equal(root.get(BookingConstants.Entity.STATE), bookingState),
                        builder.lessThan(root.get(BookingConstants.Entity.START_TIME), start)
                )
        );

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Booking> persistRecurrentBookings(List<Booking> bookings) {
        bookings.forEach(entityManager::persist);

        return bookings;

    }

    /*
     * Set the state to Cancelled, sum and duration to 0 for all booking with given
     * path and value of path
     *
     * @param path the given path
     * @param bookingId the given value of path
     * @return the number of entities deleted
     */
    private int cancelBookingsWherePathAndValue(String path, long value) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Booking> update = builder.createCriteriaUpdate(Booking.class);
        Root<Booking> root = update.from(Booking.class);

        update
                .set(root.get(BookingConstants.Entity.STATE), BookingState.CANCELLED)
                .set(root.get(BookingConstants.Entity.SUM), 0L)
                .set(root.get(BookingConstants.Entity.DURATION), 0L)
                .where(builder.equal(
                        root.get(path), value));

        return entityManager.createQuery(update).executeUpdate();
    }
}
