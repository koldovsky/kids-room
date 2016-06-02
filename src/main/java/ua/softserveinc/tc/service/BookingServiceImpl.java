package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.EntityConstants.BookingConst;
import ua.softserveinc.tc.constants.EntityConstants.UserConst;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.util.BookingUtil;
import ua.softserveinc.tc.util.DateUtil;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking> implements BookingService
{
    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private RateService rateService;

    @Override
    public List<Booking> getBookingsByRangeOfTime(String startDate, String endDate)
    {
        return getBookingsByUserByRangeOfTime(null, startDate, endDate);
    }

    @Override
    public List<Booking> getBookingsByUserByRangeOfTime(User user, String startDate, String endDate)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
        Root<Booking> root = criteria.from(Booking.class);

        List<Predicate> restrictions = new ArrayList<>(Arrays.asList(
                builder.equal(root.get(BookingConst.STATE), BookingState.COMPLETED),
                builder.between(root.get(BookingConst.START_TIME),
                dateUtil.toDate(startDate), dateUtil.toDate(endDate)))
        );

        if (user == null)
            criteria.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
        else
        {
            restrictions.add(builder.equal(root.get(UserConst.ID), user));
            criteria.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
        }

        criteria.orderBy(builder.asc(root.get(BookingConst.START_TIME)));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public void calculateAndSetDuration(Booking booking)
    {
        long difference = booking.getBookingEndTime().getTime() -
                booking.getBookingStartTime().getTime();

        booking.setDuration(difference);
    }

    @Override
    public void calculateAndSetSum(Booking booking)
    {
        calculateAndSetDuration(booking);

        List<Rate> rates = booking.getIdRoom().getRates();
        Rate closestRate = rateService.calculateClosestRate(booking.getDuration(), rates);

        booking.setSum(closestRate.getPriceRate());
        bookingDao.update(booking);
    }

    @Override
    public Long getSumTotal(List<Booking> bookings)
    {
        return bookings.stream()
            .mapToLong(Booking::getSum)
            .sum();
    }

    @Override
    public Map<User, Long> generateAReport(List<Booking> bookings)
    {
        return bookings.stream()
            .collect(Collectors.groupingBy(Booking::getIdUser,
                Collectors.summingLong(Booking::getSum)));
    }

    @Override
    public Map<Room, Long> generateStatistics(List<Booking> bookings)
    {
        return bookings.stream()
            .collect(Collectors.groupingBy(Booking::getIdRoom,
                Collectors.summingLong(Booking::getSum)));
    }

    @Override
    public List<Booking> getBookingsWithZeroSum()
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        query.where(builder.equal(root.get(BookingConst.SUM), 0))
                .where(builder.equal(root.get(BookingConst.STATE), BookingState.COMPLETED));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Booking> getTodayBookingsByRoom(Room room){
        Calendar toDay = Calendar.getInstance();
        toDay.setTime(new Date("2016/06/20")); // temporarily: for example;
        toDay.set(Calendar.AM_PM, 0);
        toDay.set(Calendar.HOUR, BookingUtil.BOOKING_START_HOUR);
        toDay.set(Calendar.MINUTE, BookingUtil.BOOKING_START_MINUTE);
        toDay.set(Calendar.SECOND, BookingUtil.BOOKING_START_SECOND);
        Date startTime = toDay.getTime();
        toDay.set(Calendar.HOUR, BookingUtil.BOOKING_END_HOUR);
        toDay.set(Calendar.MINUTE, BookingUtil.BOOKING_END_MINUTE);
        toDay.set(Calendar.SECOND, BookingUtil.BOOKING_END_SECOND);
        Date endTime = toDay.getTime();
        return bookingDao.getBookingsByDay(startTime, endTime, room);
    }

    @Override
    public Booking confirmBookingStartTime(BookingDTO bookingDTO){
        Booking booking = findById(bookingDTO.getId());
        Date date = getDateAndTimeBooking(booking, bookingDTO.getStartTime());
        booking.setBookingStartTime(date);
        return booking;
    }

    @Override
    public Booking confirmBookingEndTime(BookingDTO bookingDTO){
        Booking booking = findById(bookingDTO.getId());
        Date date = getDateAndTimeBooking(booking, bookingDTO.getEndTime());
        booking.setBookingEndTime(date);
        return booking;
    }
    @Override
    public Date getDateAndTimeBooking(Booking booking, String time) {
        DateFormat dfDate = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
        String dateString = dfDate.format(booking.getBookingStartTime()) + " " + time;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateUtil.toDateAndTime(dateString));
        return calendar.getTime();
    }
}


