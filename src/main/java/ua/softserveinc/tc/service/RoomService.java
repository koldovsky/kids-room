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
}
