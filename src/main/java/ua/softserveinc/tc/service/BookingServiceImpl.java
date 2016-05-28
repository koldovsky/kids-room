package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking> implements BookingService
{
    @Autowired
    private BookingDao bookingDao;

    @Autowired
    RateService rateService;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<Booking> getBookingsWithZeroSum()
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        query.where(builder.equal(root.get("sum"), 0));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Booking> getBookingsByRangeOfTime(String startDate, String endDate)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        try
        {
            query.where(builder.between(root.get("bookingStartTime"),
                    dateFormat.parse(startDate), dateFormat.parse(endDate)),
                    builder.equal(root.get("isCancelled"), false));
        }
        catch (ParseException e)
        {
            System.out.println("Wrong format of date. " + e.getMessage());
        }

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Booking> getBookingsByUserByRangeOfTime(User user, String startDate, String endDate)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        try
        {
            query.where(builder.between(root.get("bookingStartTime"),
                    dateFormat.parse(startDate), dateFormat.parse(endDate)),
                    builder.equal(root.get("idUser"), user),
                    builder.equal(root.get("isCancelled"), false))
                    .orderBy(builder.asc(root.get("bookingStartTime")));
        }
        catch (ParseException e)
        {
            System.out.println("Wrong format of date. " + e.getMessage());
        }

        return entityManager.createQuery(query).getResultList();
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
    @SuppressWarnings("unchecked")
    public List<Booking> getBookingsByDay(String date) throws ParseException{

        String startTimeString = date + " 00:00";
        String endTimeString = date + " 23:59";
        Calendar calendarStartTime = Calendar.getInstance();
        Calendar calendarEndTime = Calendar.getInstance();

        DateFormat dfDateAndTime = new SimpleDateFormat(DateConst.DATE_AND_TIME_FORMAT);
        calendarStartTime.setTime(dfDateAndTime.parse(startTimeString));
        calendarEndTime.setTime(dfDateAndTime.parse(endTimeString));

        Date startTime = calendarStartTime.getTime();
        Date endTime = calendarEndTime.getTime();
        List<Booking> bookings= bookingDao.getBookingsByDay(startTime, endTime);

        return bookings;
    }

    @Override
    public HashMap<User, Integer> generateAReport(List<Booking> bookings)
    {
        HashMap<User, Integer> result = new HashMap<>();

        // get set of unique users
        HashSet<User> users = new HashSet<>();
        bookings.forEach(booking -> users.add(booking.getIdUser()));

        // get sum total for each user
        for (User user : users)
        {
            int sum = 0;
            for (Booking booking : bookings)
            {
                if (booking.getIdUser().equals(user))
                {
                    sum += booking.getSum();
                }
            }
            result.put(user, sum);
        }
        return result;
    }

    @Override
    public void calculateDuration(Booking booking)
    {
        long difference = booking.getBookingEndTime().getTime() -
                booking.getBookingStartTime().getTime();

        booking.setDuration(difference);
    }

    @Override
    public void calculateSum(Booking booking)
    {
        calculateDuration(booking);

        List<Rate> rates = booking.getIdRoom().getRates();
        Rate closestRate = rateService.calculateClosestRate(booking.getDuration(), rates);
        booking.setSum(closestRate.getPriceRate());
    }

    @Override
    public Booking confirmBookingStartTime(BookingDTO bookingDTO) throws ParseException{
        Booking booking = findById(bookingDTO.getId());
        Date date = getDateAndTimeBooking(booking, bookingDTO.getStartTime());
        booking.setBookingStartTime(date);
        update(booking);
        calculateSum(booking);
        return booking;
    }
    @Override
    public Booking confirmBookingEndTime(BookingDTO bookingDTO) throws ParseException{
        Booking booking = findById(bookingDTO.getId());
        Date date = getDateAndTimeBooking(booking, bookingDTO.getEndTime());
        booking.setBookingEndTime(date);
        update(booking);
        calculateSum(booking);
        return booking;
    }

    private Date getDateAndTimeBooking(Booking booking, String time) throws ParseException {

        DateFormat dfDate = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
        DateFormat dfDateAndTime = new SimpleDateFormat(DateConst.DATE_AND_TIME_FORMAT);
        String dateString = dfDate.format(booking.getBookingStartTime()) + " " + time;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dfDateAndTime.parse(dateString));
        Date date = calendar.getTime();
        return date;

    }
}


