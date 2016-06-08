package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookingService extends BaseService<Booking>
{

    void calculateAndSetSum(Booking booking);
    void calculateAndSetDuration(Booking booking);
    Long getSumTotal(List<Booking> bookings);

    Map<User, Long> generateAReport(List<Booking> bookings);
    Map<Room, Long> generateStatistics(List<Booking> bookings);
    List<Booking> getBookings(Date startDate, Date endDate);
    List<Booking> getBookings(Date startDate, Date endDate, User user);
    List<Booking> getBookings(Date startDate, Date endDate, Room room);
    List<Booking> getBookings(Date startDate, Date endDate, User user, Room room);
    List<Booking> filterByState(List<Booking> bookings, BookingState bookingState);
    List<Booking> filterByNotState(List<Booking> bookings, BookingState bookingState);
    List<Booking> filterBySum(List<Booking> bookings, Long sum);

    Booking confirmBooking(BookingDto bookingDto);
    Booking confirmBookingEndTime(BookingDto bookingDto);
    Booking confirmBookingStartTime(BookingDto bookingDto);
    Date replaceBookingTime(Booking booking, String time);

    List<BookingDto> persistBookingsFromDTOandSetID(List<BookingDto> listDTO);
}