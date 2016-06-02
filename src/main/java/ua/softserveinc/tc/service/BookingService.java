package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookingService extends BaseService<Booking>
{
    List<Booking> getBookingsWithZeroSum();
    void calculateAndSetSum(Booking booking);
    Long getSumTotal(List<Booking> bookings);
    void calculateAndSetDuration(Booking booking);
    List<Booking> getTodayBookingsByRoom(Room room);
    Booking confirmBookingEndTime(BookingDTO bookingDTO);
    Booking confirmBookingStartTime(BookingDTO bookingDTO);
    Map<User, Long> generateAReport(List<Booking> bookings);
    Date getDateAndTimeBooking(Booking booking, String time);
    Map<Room, Long> generateStatistics(List<Booking> bookings);
    List<Booking> getBookingsByRangeOfTime(String startDate, String endDate);
    List<Booking> getBookingsByUserByRangeOfTime(User user, String startDate, String endDate);
}