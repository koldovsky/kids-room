package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
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
            builder.equal(root.get("isCancelled"), false),
            builder.between(root.get("bookingStartTime"),
                dateUtil.toDate(startDate), dateUtil.toDate(endDate)))
        );

        if (user == null)
            criteria.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
        else
        {
            restrictions.add(builder.equal(root.get("idUser"), user));
            criteria.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
            criteria.orderBy(builder.asc(root.get("bookingStartTime")));
        }

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
    }

    @Override
    public Long getSumTotal(List<Booking> bookings)
    {
        Long sumTotal = 0L;
        for (Booking booking : bookings)
        {
            sumTotal += booking.getSum();
        }

        return sumTotal;
    }

    @Override
    public <T> HashMap<T, Long> generateAReport(List<T> entities, List<Booking> bookings)
    {
        HashMap<T, Long> report = new HashMap<>();

        for (T entity : entities)
        {
            Long sum = 0L;
            for (Booking booking : bookings)
            {
                if (booking.getIdUser().equals(entity)
                    || booking.getIdRoom().equals(entity))
                {
                    sum += booking.getSum();
                }
            }
            report.put(entity, sum);
        }
        return report;
    }

    @Override
    public List<Booking> getBookingsWithZeroSum()
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        query.where(builder.equal(root.get("sum"), 0))
                .where(builder.equal(root.get("is_cancelled"), false));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Booking> getBookingsByRoom(Room room){

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
        update(booking);
        calculateAndSetSum(booking);
        return booking;
    }

    @Override
    public Booking confirmBookingEndTime(BookingDTO bookingDTO){
        Booking booking = findById(bookingDTO.getId());
        Date date = getDateAndTimeBooking(booking, bookingDTO.getEndTime());
        booking.setBookingEndTime(date);
        update(booking);
        calculateAndSetSum(booking);
        return booking;
    }

    private Date getDateAndTimeBooking(Booking booking, String time) {

        DateFormat dfDate = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
        String dateString = dfDate.format(booking.getBookingStartTime()) + " " + time;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateUtil.toDateAndTime(dateString));
        return calendar.getTime();

    }
}


