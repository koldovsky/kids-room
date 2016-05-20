package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface BookingService extends BaseService<Booking>
{
    String getCurrentDate();
    String getDateMonthAgo();
    void calculateSum(Booking booking);
    void calculateDuration(Booking booking);
    int getSumTotal(List<Booking> bookings);
    List<Booking> getBookingsByDay(String data) throws ParseException;
    HashMap<User, Integer> generateAReport(List<Booking> bookings);
    List<Booking> getBookingsByRangeOfTime(String startDate, String endDate);
    List<User> getActiveUsersForRangeOfTime(String startDate, String endDate);
    List<Booking> getBookingsByUserByRangeOfTime(User user, String startDate, String endDate);
    Booking updatingBooking(BookingDTO bookingDTO) throws ParseException;
}