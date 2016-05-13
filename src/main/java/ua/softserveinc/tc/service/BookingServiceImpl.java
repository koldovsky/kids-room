package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import java.util.*;

import static ua.softserveinc.tc.constants.ColumnConstants.BookingConst.BOOKING_START_TIME;

/**
 * Created by TARAS on 01.05.2016.
 */

@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking> implements BookingService
{
    //TODO: FOR DEMIAN: Change all methods from HQL to Criterias
    @Autowired
    private BookingDao bookingDao;

    @Override
    public List<Booking> getBookingsByUser(User user)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        List<Booking> bookings = (List<Booking>) entityManager.createQuery(
                "from Booking where idUser = " + user.getId() +
                        " order by bookingStartTime")
                .getResultList();
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByRangeOfTime(String startDate, String endDate)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        List<Booking> bookings = entityManager.createQuery(
                "from Booking where bookingStartTime" +
                        " between '" + startDate +  "' and '" + endDate + "'" +
                        " order by bookingStartTime")
                .getResultList();
        return bookings;
    }

    @Override
    public List<Booking> getActiveUsersForRangeOfTime(String startDate, String endDate)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        List<Booking> bookings = entityManager.createQuery(
                "from Booking where bookingStartTime" +
                        " between '" + startDate +  "' and '" + endDate + "'" +
                        " group by idUser")
                .getResultList();
        return bookings;
    }

    public List<Booking> getBookingsByUserByRangeOfTime(User user, String startDate, String endDate)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        List<Booking> bookings = entityManager.createQuery(
                "from Booking where bookingStartTime" +
                        " between '" + startDate +  "' and '" + endDate + "'" +
                        " and idUser = " + user.getId() +
                        " order by bookingStartTime")
                .getResultList();
        return bookings;
    }

    @Override
    public List<Booking> getBookingsOfThisDay()
    {
        //Date date = new Date();
        EntityManager entityManager = bookingDao.getEntityManager();
        List<Booking> bookingsDay = (List<Booking>) entityManager.createQuery(
                "from Booking where " + BOOKING_START_TIME + " = '2015-04-04 00:00:00'")
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

        // get total sum for each user
        for(User user : users)
        {
            Integer sum = 0;
            for (Booking booking : bookings)
            {
                if (booking.getIdUser().equals(user))
                {
                    sum += booking.getPrice(booking.getDifference());
                }
            }
            result.put(user, sum);
        }
        return result;
    }
}
