package ua.softserveinc.tc.service;

import org.springframework.http.ResponseEntity;
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

    List<Booking> getBookings(Date startDate, Date endDate, Room room, boolean includeOneDay,
                              BookingState... bookingStates);

    List<Booking> getBookings(Date startDate, Date endDate, User user, Room room,
                              boolean includeLastDay, BookingState... bookingStates);

    Booking confirmBookingEndTime(BookingDto bookingDto);

    Booking confirmBookingStartTime(BookingDto bookingDto);

    Date replaceBookingTime(Booking booking, String time);

    BookingDto getRecurrentBookingForEditingById(long bookingId);

    List<BookingDto> updateRecurrentBookings(BookingDto recurrentBookingDtos);

    List<BookingDto> getAllBookingsByUserAndRoom(Long idUser, Long idRoom);

    /**
     * Returns list all of the bookings that have booking states BookingState.BOOKED
     * and BookingState.Active. If any of the parameter is null the method will return
     * the empty list.
     *
     * @param startDate the start date of the given period
     * @param endDate   the end date of the given period
     * @param room      the given room of bookings
     * @return the list of bookings
     */
    List<Booking> getNotCompletedAndCancelledBookings(Date startDate, Date endDate, Room room);

    /**
     * Checks if there is a duplicated bookings in the given list of BookingDto.
     * The given list should not be empty or null.
     *
     * @param listDto the list of objects of BookingsDto
     * @return true if there is a duplicate bookings, otherwise return false.
     */
    boolean hasDuplicateBookings(List<BookingDto> listDto);

    /**
     * Persists the list of bookings objects that are created from the
     * given BookingDto objects. The given list should not be empty or null.
     * If there are no available places in the room or if the given list is empty
     * than empty unmodifiable list will be returned.
     *
     * @param listDTO the given list of the BookingDto objects.
     * @return the list of BookingDto objects that represents the persisted
     * bookings
     */
    List<BookingDto> persistBookingsFromDto(List<BookingDto> listDTO);

    /**
     * Receives the list of BookingDto objects. Then validates the input parameter for
     * correctness. If any of the input parameters are not correct or the system failed
     * to persist all of the bookings from the dto then method returns ResponseEntity
     * with "Bad Request" http status (400). Otherwise returns list of the persisted
     * Bookings in the BookingsDto objects in the body of object of ResponseEntity
     * with http status "OK" (200).
     *
     * @param bookingDtos list of BookingsDto objects
     * @return ResponseEntity with appropriate http status and body that consists list of
     * the BookingsDto objects that represents persisted bookings
     */
    ResponseEntity<String> makeRecurrentBookings(List<BookingDto> bookingDtos);

    /**
     * Normalizes the list of BookingDto objects. Set if not exists room,
     * user, child, idChild, start date and end date. The given list should not
     * be empty or null. If given BookingDto object has any not correct corresponding
     * field, then method returns false, otherwise return true.
     *
     * @param dtoList the list of BookingDto objects
     * @return true if operation was success, otherwise - false
     */
    boolean normalizeBookingDtoObjects(List<BookingDto> dtoList);
}
