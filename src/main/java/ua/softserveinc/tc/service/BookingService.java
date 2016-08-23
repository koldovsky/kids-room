package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookingService extends BaseService<Booking> {

    void calculateAndSetSum(Booking booking);
    void calculateAndSetDuration(Booking booking);
    Long getSumTotal(List<Booking> bookings);

    Map<User, Long> generateAReport(List<Booking> bookings);
    Map<Room, Long> generateStatistics(List<Booking> bookings);

    List<Booking> getBookings(Date startDate, Date endDate, BookingState... bookingStates);
    List<Booking> getBookings(Date startDate, Date endDate, User user, BookingState... bookingStates);
    List<Booking> getBookings(Date startDate, Date endDate, Room room, BookingState... bookingStates);
    List<Booking> getBookings(Date startDate, Date endDate, User user, Room room, BookingState... bookingStates);

    Booking confirmBookingEndTime(BookingDto bookingDto);
    Booking confirmBookingStartTime(BookingDto bookingDto);

    Date replaceBookingTime(Booking booking, String time);

    Boolean checkForDuplicateBooking(List<BookingDto> listDto);

    List<BookingDto> persistBookingsFromDtoAndSetId (List<BookingDto> listDTO);
    List<BookingDto> getAllBookingsByUserAndRoom(Long idUser, Long idRoom);

    Long getMaxRecurrentId();
    List<BookingDto> makeRecurrentBookings(List<BookingDto> bookingDtos);
}