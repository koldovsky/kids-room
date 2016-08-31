package ua.softserveinc.tc.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.ApplicationConfigurator;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

@Service
public class RoomServiceImpl extends BaseServiceImpl<Room> implements RoomService {

    @Autowired
    private ApplicationConfigurator appConfigurator;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private BookingService bookingService;

    private static
    @Log
    Logger log;

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
    public Map<String, String> getBlockedPeriods(Room room, Calendar start, Calendar end) {
        Map<String, String> result = new TreeMap<>();
        while (start.compareTo(end) < 0) {
            result.putAll(getBlockedPeriodsForDay(room, start));
            start.add(Calendar.DAY_OF_MONTH, 1);
        }

        if ( !result.isEmpty()) {
            Set keys = result.keySet();
            Iterator i = keys.iterator();
            String baseKey = (String) i.next();
            while (i.hasNext()) {
                try {
                    String nextKey = (String) i.next();
                    String value = result.get(baseKey);
                    if (value.compareTo(nextKey) == 0) {

                        result.put(baseKey, result.get(nextKey));
                        i.remove();
                    } else {
                        i.remove();

                        baseKey = (String) i.next();
                    }
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
        }

        return result;
    }

    private Map<String, String> getBlockedPeriodsForDay(Room room, Calendar calendarStart) {
        DateFormat timeFormat = new SimpleDateFormat(DateConstants.TIME_FORMAT);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(calendarStart.getTime());

        Map<String, String> result = new TreeMap<>();

        try {
            Calendar temp = Calendar.getInstance();
            temp.setTime(timeFormat.parse(room.getWorkingHoursStart()));
            calendarStart.set(Calendar.HOUR_OF_DAY, temp.get(Calendar.HOUR_OF_DAY));
            calendarStart.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));
            calendarStart.set(Calendar.SECOND, 0);

            temp.setTime(timeFormat.parse(room.getWorkingHoursEnd()));
            calendarEnd.set(Calendar.HOUR_OF_DAY, temp.get(Calendar.HOUR_OF_DAY));
            calendarEnd.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));
            calendarEnd.set(Calendar.SECOND, 0);
        } catch (ParseException pe) {
            log.error("Could not parse date", pe);
            return result;
        }

        List<Booking> bookings = reservedBookings(calendarStart.getTime(),
                calendarEnd.getTime(), room);

        Calendar temp = Calendar.getInstance();
        temp.setTime(calendarStart.getTime());

        int minPeriod = appConfigurator.getMinPeriodSize();
        while (calendarStart.compareTo(calendarEnd) < 0) {

            temp.add(Calendar.MINUTE, minPeriod);

            Date lo = calendarStart.getTime();
            Date hi = temp.getTime();

            List<Booking> tempList = bookings.stream()
                    .filter(booking ->
                            booking.getBookingStartTime().compareTo(hi) < 0
                                    && booking.getBookingEndTime().compareTo(lo) > 0)
                    .collect(Collectors.toList());

            if (room.getCapacity() <= tempList.size()) {
                result.put(DateUtil.convertDateToString(lo),
                        DateUtil.convertDateToString(hi));
            }
            calendarStart.setTime(temp.getTime());
        }


        return result;
    }


    @Override
    public Boolean isPossibleUpdate(BookingDto bookingDto) {
        Booking booking = bookingService.findById(bookingDto.getId());
        Room room = booking.getRoom();
        Date dateLo = toDateAndTime(bookingDto.getStartTime());
        Date dateHi = toDateAndTime(bookingDto.getEndTime());
        List<Booking> list = reservedBookings(dateLo, dateHi, room);
        if (list.contains(booking)) {
            list.remove(booking);
            return room.getCapacity() > list.size();
        } else {
            return room.getCapacity() > list.size();
        }
    }

    private List<Booking> reservedBookings(Date dateLo, Date dateHi, Room room) {
        return roomDao.reservedBookings(dateLo, dateHi, room);
    }

    /**
     * @param dateLo start of period
     * @param dateHi end of period
     * @param room   a requested room
     * @return number of places available in the room for the period
     */
    public Integer getAvailableSpaceForPeriod(Date dateLo, Date dateHi, Room room) {
        List<Booking> bookings = reservedBookings(dateLo, dateHi, room);
        return room.getCapacity() - bookings.size();
    }

    @Override
    public List<Room> getActiveRooms() {
        return roomDao.findAll().stream()
                .filter(Room::isActive)
                .collect(Collectors.toList());
    }


}
