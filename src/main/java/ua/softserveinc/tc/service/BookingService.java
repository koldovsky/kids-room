package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDtosss;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookingService extends BaseService<Booking>
{
    List<Booking> getBookingsWithZeroSum();
    void calculateAndSetSum(Booking booking);
    Long getSumTotal(List<Booking> bookings);
    void calculateAndSetDuration(Booking booking);
    Map<User, Long> generateAReport(List<Booking> bookings);
    Map<Room, Long> generateStatistics(List<Booking> bookings);
    List<Booking> getBookings(String startDate, String endDate);
    List<Booking> getBookings(User user, String startDate, String endDate);
    List<Booking> getBookings(Room room, String startDate, String endDate);
    List<Booking> getBookings(User user, Room room, String startDate, String endDate);

    Map<String, String> getBlockedPeriodsForWeek(Room room);
    Map<String, String> getBlockedPeriodsForDay(Room room, Calendar day);
    List<Booking> getTodayNotCancelledBookingsByRoom(Room room);
    List<BookingDtosss> persistBookingsFromDTOandSetID(List<BookingDtosss> listDTO);
    Boolean checkFreePlaces (Room room, String startTime, String endTime);
    Boolean isPeriodAvailable(Room room, Date dateLo, Date dateHi);
    List<Booking> getTodayBookingsByRoom(Room room);
    Booking confirmBookingEndTime(BookingDtosss bookingDto);
    Booking confirmBookingStartTime(BookingDtosss bookingDto);
    Date replaceBookingTime(Booking booking, String time);
}