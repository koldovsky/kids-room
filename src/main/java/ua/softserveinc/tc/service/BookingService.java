package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface BookingService extends BaseService<Booking>
{
    void calculateSum(Booking booking);
    void calculateDuration(Booking booking);
    Double getSumTotal(List<Booking> bookings);
    HashMap<User, Integer> generateAReport(List<Booking> bookings);
    List<Booking> getBookingsByDay(String data) throws ParseException;
    List<Booking> getBookingsByRangeOfTime(String startDate, String endDate);
    Booking confirmBookingEndTime(BookingDTO bookingDTO) throws ParseException;
    Booking confirmBookingStartTime(BookingDTO bookingDTO) throws ParseException;
    List<Booking> getBookingsByUserByRangeOfTime(User user, String startDate, String endDate);
}