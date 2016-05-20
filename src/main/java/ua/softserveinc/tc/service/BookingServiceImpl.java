package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dto.BookingDTO;
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
    public List<Booking> getBookingsByDay(String date) throws ParseException{

        String startTimeString = date + " 00:00";
        String endTimeString = date + " 23:00";
        Calendar calendarStartTime = Calendar.getInstance();
        Calendar calendarEndTime = Calendar.getInstance();

        DateFormat dfDateAndTime = new SimpleDateFormat(DateConst.DATE_AND_MINUTE_FORMAT);
        calendarStartTime.setTime(dfDateAndTime.parse(startTimeString));
        calendarEndTime.setTime(dfDateAndTime.parse(endTimeString));

        Date startTime = calendarStartTime.getTime();
        Date endTime = calendarEndTime.getTime();
        List<Booking> bookings= bookingDao.getBookingsByDay(startTime, endTime);

        return bookings;
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

        long hours = difference / 1000 / 60 / 60;
        long minutes = difference / 1000 / 60 % 60;

        String duration = String.format("%02d", hours) + ":";
        duration += String.format("%02d", minutes);

        booking.setDuration(duration);
    }

    @Override
    public void calculateSum(Booking booking)
    {
        calculateDuration(booking);
        String hoursAndMinutes = booking.getDuration();
        int hours = Integer.parseInt(hoursAndMinutes.substring(0, 2));
        int minutes = Integer.parseInt(hoursAndMinutes.substring(3));

        // 02:00 hours - 2 hours; 02:01 hours - 3 hours
        if (minutes > 0) hours++;

        // get prices for particular room and sort them in order to choose appropriate one
        Map<Integer, Long> prices = booking.getIdRoom().getPrices();
        ArrayList<Integer> listOfKeys = new ArrayList<>();
        listOfKeys.addAll(prices.keySet());
        Collections.sort(listOfKeys);

        while (true) {
            if (listOfKeys.contains(hours)) {
                booking.setSum(prices.get(hours));
                break;
            }
            hours++;
            // if manager enters value that is bigger than max value in the list
            // we return price for max value
            if (hours > 10) {
                booking.setSum(prices.get(listOfKeys.get(listOfKeys.size() - 1)));
                break;
            }
        }
    }

    @Override
    public Booking updatingBooking(BookingDTO bookingDTO) throws ParseException{
        DateFormat dfDate = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
        DateFormat dfDateAndTime = new SimpleDateFormat(DateConst.DATE_AND_MINUTE_FORMAT);
        Booking booking = findById(bookingDTO.getId());
        calculateSum(booking);
        String dateString = dfDate.format(booking.getBookingStartTime()) + " " + bookingDTO.getStartTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dfDateAndTime.parse(dateString));
        Date date = calendar.getTime();
        booking.setBookingStartTime(date);
        update(booking);
        return booking;
    }
}


