package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

import static ua.softserveinc.tc.constants.ColumnConstants.BookingConst.BOOKING_START_TIME;

@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking> implements BookingService
{
    @Autowired
    private BookingDao bookingDao;

    @Override
    public List<Booking> getBookingsByRangeOfTime(String startDate, String endDate)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);

        Root<Booking> root = query.from(Booking.class);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try
        {
            query.where(builder.between(root.get("bookingStartTime"),
                    dateFormat.parse(startDate), dateFormat.parse(endDate)));
        }
        catch (ParseException e)
        {
            System.out.println("Wrong format of date. " + e.getMessage());
        }

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<User> getActiveUsersForRangeOfTime(String startDate, String endDate)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        Root<Booking> root = query.from(Booking.class);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try
        {
            query.select(root.get("idUser"))
                    .where(builder.between(root.get("bookingStartTime"),
                dateFormat.parse(startDate), dateFormat.parse(endDate)))
                .groupBy(root.get("idUser"));
        }
        catch (Exception e)
        {
            System.out.println("Wrong format of date. " + e.getMessage());
        }

        TypedQuery<User> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public List<Booking> getBookingsByUserByRangeOfTime(User user, String startDate, String endDate)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);

        Root<Booking> root = query.from(Booking.class);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try
        {
            query.where(builder.between(root.get("bookingStartTime"),
                    dateFormat.parse(startDate), dateFormat.parse(endDate)),
                    builder.equal(root.get("idUser"), user))
                    .orderBy(builder.asc(root.get("bookingStartTime")));
        }
        catch (ParseException e)
        {
            System.out.println("Wrong format of date. " + e.getMessage());
        }

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Booking> getBookingsOfThisDay()
    {
        Date day = new Date("2015/04/04");
        SimpleDateFormat df = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
        String currentDay = df.format(day);
        EntityManager entityManager = bookingDao.getEntityManager();
        List<Booking> bookingsDay = (List<Booking>) entityManager.createQuery(
                "from Booking where " + BOOKING_START_TIME + " like " + "'"+currentDay+"%'")
                .getResultList();
        return bookingsDay;
    }

    @Override
    public int getSumTotal(List<Booking> bookings)
    {
        int sumTotal = 0;
        for (Booking booking : bookings)
        {
            sumTotal += booking.getSum();
        }

        return sumTotal;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Booking> getBookingsByDay(String data){

        EntityManager entityManager = bookingDao.getEntityManager();

        List<Booking> bookingsDay = (List<Booking>) entityManager.createQuery(
                "from Booking where " + BOOKING_START_TIME + " like " + "'"+data+"%'")
                .getResultList();

        return bookingsDay;
    }

    @Override
    public String getCurrentDate()
    {
        Calendar calendar = Calendar.getInstance();
        String dateNow = calendar.get(Calendar.YEAR) + "-";
        dateNow += String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-";
        dateNow += String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
        return dateNow;
    }

    @Override
    public String getDateMonthAgo()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String dateThen = calendar.get(Calendar.YEAR) + "-";
        dateThen += String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-";
        dateThen += String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
        return dateThen;
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
            Integer sum = 0;
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
    public String getDuration(Booking booking)
    {
        String duration = "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(booking.getBookingStartTime());
        int startHour = calendar.get(Calendar.HOUR_OF_DAY);
        int startMinute = calendar.get(Calendar.MINUTE);

        calendar.setTime(booking.getBookingEndTime());
        int endHour = calendar.get(Calendar.HOUR_OF_DAY);
        int endMinute = calendar.get(Calendar.MINUTE);

        int differenceHour = endHour - startHour;
        int differenceMinute = endMinute - startMinute;
        if (differenceMinute < 0) {differenceHour--; differenceMinute += 60;}

        duration += String.format("%02d", differenceHour) + ":";
        duration += String.format("%02d", differenceMinute);
        return duration;
    }
}
