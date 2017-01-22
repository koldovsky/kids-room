package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;

import java.util.Date;
import java.util.List;

public interface RoomService extends BaseService<Room> {

    void saveOrUpdate(Room room);

    /**
     * Figures out all time periods where given room is full i.e. there are no
     * available places in the room. If input parameter is null or is not existed
     * room Id then method returns empty unmodifiable list.
     *
     * @param roomID the given room id
     * @return the list of time periods
     */
    List<Date[]> getDisabledPeriods(Long roomID);

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
