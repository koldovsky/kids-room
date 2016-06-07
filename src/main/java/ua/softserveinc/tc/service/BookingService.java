package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDto;
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
    List<Booking> getBookings(String startDate, String endDate, User user);
    List<Booking> getBookings(String startDate, String endDate, Room room);
    List<Booking> getBookings(String startDate, String endDate, User user, Room room);

    Map<String, String> getBlockedPeriodsForWeek(Room room);
    Map<String, String> getBlockedPeriodsForDay(Room room, Calendar day);
    List<Booking> getTodayNotCancelledBookingsByRoom(Room room);
    List<BookingDto> persistBookingsFromDTOandSetID(List<BookingDto> listDTO);
    Boolean checkFreePlaces (Room room, String startTime, String endTime);
    Boolean isPeriodAvailable(Room room, Date dateLo, Date dateHi);
    List<Booking> getTodayBookingsByRoom(Room room);
    Booking confirmBookingEndTime(BookingDto bookingDto);
    Booking confirmBookingStartTime(BookingDto bookingDto);
    Date replaceBookingTime(Booking booking, String time);
}