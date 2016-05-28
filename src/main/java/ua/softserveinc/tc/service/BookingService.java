package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;

import java.util.HashMap;
import java.util.List;

public interface BookingService extends BaseService<Booking>
{
    List<Booking> getBookingsWithZeroSum();
    void calculateAndSetSum(Booking booking);
    Long getSumTotal(List<Booking> bookings);
    List<Booking> getBookingsByDay(String data);
    void calculateAndSetDuration(Booking booking);
    Booking confirmBookingEndTime(BookingDTO bookingDTO);
    Booking confirmBookingStartTime(BookingDTO bookingDTO);
    List<Booking> getBookingsByRangeOfTime(String startDate, String endDate);
    <T> HashMap<T, Long> generateAReport(List<T> entities, List<Booking> bookings);
    List<Booking> getBookingsByUserByRangeOfTime(User user, String startDate, String endDate);
}