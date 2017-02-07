package ua.softserveinc.tc.service.impl;

import org.slf4j.Logger;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.BookingsCharacteristics;
import ua.softserveinc.tc.util.Log;

import java.time.LocalDate;
import java.util.List;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.util.DateUtil.toDateISOFormat;

@Service
public class RoomServiceImpl extends BaseServiceImpl<Room> implements RoomService {

    @Log
    private static Logger log;

    @Inject
    private RoomDao roomDao;

    @Inject
    private BookingService bookingService;

    @Inject
    private BookingDao bookingDao;

    @Override
    public List<Room> findAll() {

        return roomDao.findAll();
    }

    @Override
    @Transactional
    public void saveOrUpdate(Room room) {

        roomDao.saveOrUpdate(room);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Date[]> getDisabledPeriods(Long roomID) {
        List<Date[]> result = Collections.emptyList();

        if(roomID != null) {
            Room room = roomDao.findById(roomID);
            if(room != null) {
                result = bookingService.getAllNotAvailablePlacesTimePeriods(room);
            }
        }

        return result;
    }

    @Override
    public Boolean isPossibleUpdate(BookingDto bookingDto) {
        Booking booking = bookingService.findByIdTransactional(bookingDto.getId());
        Room room = booking.getRoom();
        Date dateLo = toDateISOFormat(bookingDto.getStartTime());
        Date dateHi = toDateISOFormat(bookingDto.getEndTime());
        List<Booking> list = reservedBookings(dateLo, dateHi, room);
        if (list.contains(booking)) {
            list.remove(booking);

            return room.getCapacity() > list.size();
        } else {

            return room.getCapacity() > list.size();
        }
    }

    @Override
    public List<Booking> reservedBookings(Date dateLo, Date dateHi, Room room) {

        return roomDao.reservedBookings(dateLo, dateHi, room);
    }

    @Override
    public List<BookingDto> getAllFutureBookings(Room room) {
        BookingsCharacteristics characteristic = new BookingsCharacteristics.Builder()
                .setDates(new Date[] {new Date(), null})
                .setRooms(Collections.singletonList(room))
                .setBookingsStates(Collections.singletonList(BookingState.BOOKED))
                .build();

        return bookingDao.getBookings(characteristic)
                .stream()
                .map(BookingDto::new)
                .collect(Collectors.toList());
    }

    /**
     * function change room state
     * if room state is change to inactive
     * all bookings in the room are cancelled
     * @param id Room id
     */
    @Override
    public Room changeActiveState(Long id) {
        Room room = findByIdTransactional(id);
        room.setActive(!room.isActive());
        if (!room.isActive()) {
            bookingService.cancelAllActiveAndPlannedRoomBookings(room);
        }
        update(room);

        return room;
    }

    @Override
    public boolean hasPlanningBooking(Room room) {

        return !bookingService.getAllPlannedBookingsInTheRoom(room).isEmpty();
    }

    @Override
    public boolean hasActiveBooking(Room room) {

        return !bookingService.getAllActiveBookingsInTheRoom(room).isEmpty();
    }

    /**
     * @return today's active rooms for parent, based on {@link DayOff}
     */
    @Override
    public List<Room> getTodayActiveRooms() {
        LocalDate today = LocalDate.now();

        return roomDao.findByIsActiveTrue().stream()
                .filter(room -> room.getDaysOff().stream()
                        .noneMatch(day -> day.getStartDate().isEqual(today)))
                .filter(room -> room.getDaysOff().stream()
                        .noneMatch(day -> day.getEndDate().isEqual(today)))
                .filter(room -> room.getDaysOff().stream()
                        .noneMatch(day -> today.isAfter(day.getStartDate()) &&
                                today.isBefore(day.getEndDate())))
                .collect(Collectors.toList());
    }

}
