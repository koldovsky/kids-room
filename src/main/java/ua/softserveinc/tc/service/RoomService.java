package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RoomService extends BaseService<Room> {

    void saveOrUpdate(Room room);

    Map<String, String> getBlockedPeriods(
            Room room, Calendar start, Calendar end);

    Boolean isPossibleUpdate(BookingDto bookingDto);

    Integer getAvailableSpaceForPeriod(Date dateLo, Date dateHi, Room room);

    List<Room> getTodayActiveRooms();

    List<BookingDto> getAllFutureBookings(Room room);

    Room changeActiveState(Long id);

    boolean hasPlanningBooking(Room room);

    boolean hasActiveBooking(Room room);

    /**
     * Returns list of reserved booking for given period of time
     * for given room
     *
     * @param dateLo given start time
     * @param dateHi given end time
     * @param room given room
     * @return list of reserved booking
     */
    List<Booking> reservedBookings(Date dateLo, Date dateHi, Room room);

    /**
     * The method finds the maximum people in the room for period of time
     * from dateLo to dateHi. All of the parameters must not be a null.
     *
     * @param dateLo   start of period
     * @param dateHi   end of period
     * @param bookings all reserved bookings in the time period
     * @return The maximum number of people that are simultaneously in the room
     */
    int maxRangeReservedBookings(Date dateLo, Date dateHi, List<Booking> bookings);
}
