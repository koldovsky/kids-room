package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.util.BookingsCharacteristics;
import ua.softserveinc.tc.util.TwoTuple;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service for bookings.
 *
 * Rewritten by Sviatoslav Hryb on 05.10.2017
 */
public interface BookingService extends BaseService<Booking> {

    List<BookingDto> getAllActiveBookingsInTheRoom(Room room);

    List<BookingDto> getAllPlannedBookingsInTheRoom(Room room);

    void calculateAndSetSum(Booking booking);

    void calculateAndSetDuration(Booking booking);

    Long getSumTotal(List<Booking> bookings);

    Map<User, Long> generateAReport(List<Booking> bookings);

    Map<Room, Long> generateStatistics(List<Booking> bookings);

    Booking confirmBookingEndTime(BookingDto bookingDto);

    Booking confirmBookingStartTime(BookingDto bookingDto);

    Date replaceBookingTime(Booking booking, String time);

    BookingDto getRecurrentBookingForEditingById(long bookingId);

    List<BookingDto> updateRecurrentBookings(BookingDto recurrentBookingDtos);

    /**
     * Gets the all bookings by given start and end date, booking states.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param bookingStates the given booking states
     * @return the list of appropriate bookings
     */
    List<Booking> getBookings(Date startDate, Date endDate, BookingState... bookingStates);

    /**
     * Gets the all bookings by given dates, user, booking states.
     *
     * @param dates the given dates
     * @param user the given user
     * @param bookingStates the given booking states
     * @return the list of appropriate bookings
     */
    List<Booking> getBookings(Date[] dates, User user, BookingState... bookingStates);

    /**
     * Gets the all bookings by given dates, room, booking states.
     *
     * @param dates the given dates
     * @param room the given room
     * @param bookingStates the given booking states
     * @return the list of appropriate bookings
     */
    List<Booking> getBookings(Date[] dates, Room room, BookingState... bookingStates);

    /**
     * Gets the all bookings for given booking characteristics.
     *
     * @param characteristics the given characteristics
     * @return the list of appropriate bookings
     */
    List<Booking> getBookings(BookingsCharacteristics characteristics);

    /**
     * Get all completed bookings by given user id and room Id.
     *
     * @param idUser the given user id
     * @param idRoom the given room id
     * @return list of bookings
     */
    List<BookingDto> getAllBookingsByUserAndRoom(Long idUser, Long idRoom);

    /**
     * Returns list all of the bookings that have booking states BookingState.BOOKED
     * and BookingState.Active. If any of the parameter is null then that parameter
     * will be not counted
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
     * Receives the list of recurrent BookingDto objects. Then validates the input parameter
     * for correctness and persist all the objects. If any of the input parameters are not
     * correct or the system failed to persist all of the bookings from the dto then method
     * returns TwoTuple where first field is equals to null, and second equals string error
     * code for localization. Otherwise returns TwoTuple where first field is list of persisted
     * BookingDto objects, and second equals to null.
     *
     * @param bookingDtos list of BookingsDto objects
     * @return appropriate TwoTuple object
     */
    TwoTuple<List<BookingDto>, String> makeRecurrentBookings(List<BookingDto> bookingDtos);

    /**
     * Receives the list of BookingDto objects. Then validates the input parameter for
     * correctness and persist all the objects. If any of the input parameters are not
     * correct or the system failed to persist all of the bookings from the dto then method
     * returns TwoTuple where first field is equals to null, and second equals string error
     * code for localization. Otherwise returns TwoTuple where first field is list of persisted
     * BookingDto objects, and second equals to null.
     *
     * @param bookingDtos list of BookingsDto objects
     * @return appropriate TwoTuple object
     */
    TwoTuple<List<BookingDto>, String> makeBookings(List<BookingDto> bookingDtos);

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
