package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static ua.softserveinc.tc.constants.ColumnConstants.BookingConst.BOOKING_START_TIME;

/**
 * Created by TARAS on 01.05.2016.
 */

@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking> implements BookingService
{
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
}
