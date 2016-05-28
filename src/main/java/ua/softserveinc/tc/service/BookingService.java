package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface BookingService extends BaseService<Booking>
{
    void calculateSum(Booking booking);
    List<Booking> getBookingsWithZeroSum();
    void calculateDuration(Booking booking);
    Long getSumTotal(List<Booking> bookings);
    List<Booking> getBookingsByDay(String data) throws ParseException;
    List<Booking> getBookingsByRangeOfTime(String startDate, String endDate);
    Booking confirmBookingEndTime(BookingDTO bookingDTO) throws ParseException;
    Booking confirmBookingStartTime(BookingDTO bookingDTO) throws ParseException;
    HashMap<User, Long> generateAReport(List<User> users, List<Booking> bookings);
    HashMap<Room, Long> generateStatistics(List<Room> rooms, List<Booking> bookings);
    List<Booking> getBookingsByUserByRangeOfTime(User user, String startDate, String endDate);
}