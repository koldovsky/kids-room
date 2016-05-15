package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;

import java.util.HashMap;
import java.util.List;

public interface BookingService extends BaseService<Booking>
{
    String getCurrentDate();
    String getDateMonthAgo();
    List<Booking> getBookingsOfThisDay();
    List<Booking> getBookingsByDay(String data);
    HashMap<User, Integer> generateAReport(List<Booking> bookings);
    List<Booking> getBookingsByRangeOfTime(String startDate, String endDate);
    List<User> getActiveUsersForRangeOfTime(String startDate, String endDate);
    List<Booking> getBookingsByUserByRangeOfTime(User user, String startDate, String endDate);
}