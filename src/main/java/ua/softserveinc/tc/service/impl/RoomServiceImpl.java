package ua.softserveinc.tc.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.repo.RoomRepository;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.ApplicationConfigurator;
import ua.softserveinc.tc.util.BookingsCharacteristics;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.util.DateUtil.toDateISOFormat;

@Service
public class RoomServiceImpl extends BaseServiceImpl<Room>
        implements RoomService {

    @Autowired
    private ApplicationConfigurator appConfigurator;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDao bookingDao;

    @Log
    private static Logger log;

    @Override
    public List<Room> findAll() {
        return roomDao.findAll();
    }

    @Override
    @Transactional
    public void saveOrUpdate(Room room) {
        roomDao.saveOrUpdate(room);
    }

    /**
     * @param room  a requested room
     * @param start start of period
     * @param end   end of period
     * @return map containing start-end pairs representing time periods
     */
    @Override
    public Map<String, String> getBlockedPeriods(
            Room room, Calendar start, Calendar end) {
        Map<String, String> result = new TreeMap<>();
        while (start.compareTo(end) < 0) {
            result.putAll(getBlockedPeriodsForDay(room, start));
            start.add(Calendar.DAY_OF_MONTH, 1);
        }

        if (!result.isEmpty()) {
            Set<String> keys = result.keySet();
            Iterator<String> i = keys.iterator();
            String baseKey = i.next();
            while (i.hasNext()) {
                String nextKey = i.next();
                String value = result.get(baseKey);
                if (value.compareTo(nextKey) == 0) {

                    result.put(baseKey, result.get(nextKey));
                    i.remove();
                } else {
                    i.remove();

                    baseKey = i.next();
                }
            }
        }

        return result;
    }

    private Map<String, String> getBlockedPeriodsForDay(
            Room room, Calendar calendarStart) {
        DateFormat timeFormat = new SimpleDateFormat(DateConstants.TIME_FORMAT);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(calendarStart.getTime());

        Map<String, String> result = new TreeMap<>();

        try {
            Calendar temp = Calendar.getInstance();
            temp.setTime(timeFormat.parse(room.getWorkingHoursStart()));
            calendarStart.set(Calendar.HOUR_OF_DAY,
                    temp.get(Calendar.HOUR_OF_DAY));
            calendarStart.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));
            calendarStart.set(Calendar.SECOND, 0);
            calendarStart.set(Calendar.MILLISECOND, 0);

            temp.setTime(timeFormat.parse(room.getWorkingHoursEnd()));
            calendarEnd.set(Calendar.HOUR_OF_DAY,
                    temp.get(Calendar.HOUR_OF_DAY));
            calendarEnd.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));
            calendarEnd.set(Calendar.SECOND, 0);
            calendarStart.set(Calendar.MILLISECOND, 0);
        } catch (ParseException pe) {
            log.error("Could not parse date", pe);
            return result;
        }

        List<Booking> bookings = reservedBookings(calendarStart.getTime(),
                calendarEnd.getTime(), room);
        if (bookings.size() >= room.getCapacity()) {
            Calendar temp = Calendar.getInstance();
            temp.setTime(calendarStart.getTime());

            int minPeriod = appConfigurator.getMinPeriodSize();
            while (calendarStart.compareTo(calendarEnd) < 0) {

                temp.add(Calendar.MINUTE, minPeriod);

                Date lo = calendarStart.getTime();
                Date hi = temp.getTime();

                List<Booking> tempList = bookings.stream()
                        .filter(booking ->
                                booking.getBookingStartTime().before(hi)
                                        && booking.getBookingEndTime()
                                        .after(lo))
                        .collect(Collectors.toList());
                if (room.getCapacity() <= tempList.size()) {
                    result.put(DateUtil.convertDateToString(lo),
                            DateUtil.convertDateToString(hi));
                }
                calendarStart.setTime(temp.getTime());
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
    public int maxRangeReservedBookings(Date dateLo, Date dateHi, List<Booking> bookings) {
        int maxReservedBookings = 0;
        for (long ti = dateLo.getTime() + 1;
             ti < dateHi.getTime(); ti += DateConstants.ONE_MINUTE_MILLIS) {
            int temporaryMax = 0;
            for (Booking tab : bookings) {
                if (tab.getBookingStartTime().getTime() < ti &&
                        tab.getBookingEndTime().getTime() > ti) {
                    temporaryMax++;
                }
            }
            if (temporaryMax > maxReservedBookings) {
                maxReservedBookings = temporaryMax;
            }
        }
        return maxReservedBookings;
    }

    /**
     * The method finds the available space in the room (number of people)
     * for the given period of time from dateLo to dateHi.
     * All of the parameters must not be a null.
     *
     * @param dateLo start of period
     * @param dateHi end of period
     * @param room   a requested room
     * @return number of places available in the room for the period
     */
    public Integer getAvailableSpaceForPeriod(Date dateLo, Date dateHi, Room room) {
        List<Booking> bookings = reservedBookings(dateLo, dateHi, room);
        int maxReservedBookings = maxRangeReservedBookings(dateLo, dateHi, bookings);
        return room.getCapacity() - maxReservedBookings;
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
        if(!room.isActive()) {
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

        return roomRepository.findByIsActiveTrue().stream()
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
