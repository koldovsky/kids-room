package ua.softserveinc.tc.service.impl;

import org.quartz.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.PeriodDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.ApplicationConfigurator;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.Log;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.util.DateUtil.convertDateToString;
import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

@Service
public class RoomServiceImpl extends BaseServiceImpl<Room> implements RoomService {

    @Autowired
    private ApplicationConfigurator appConfigurator;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private BookingService bookingService;

    private static @Log
    Logger log;


    @Override
    public List<Room> findAll() {
        return roomDao.findAll();
    }

    @Override
    public void saveOrUpdate(Room room){
        roomDao.saveOrUpdate(room);
    }

    /**
     * @param room a requested room
     * @param start start of period
     * @param end end of period
     * @return map containing start-end pairs representing time periods
     */
    @Override
    public Map<String, String> getBlockedPeriods(Room room, Calendar start, Calendar end) {
        Map<String, String> result = new HashMap<>();
        while (start.compareTo(end) < 0) {
            result.putAll(getBlockedPeriodsForDay(room, start));
            start.add(Calendar.DAY_OF_MONTH, 1);
        }

        return result;
    }


    private Map<String, String> getBlockedPeriodsForDay(Room room, Calendar calendarStart) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(calendarStart.getTime());

        Map<String, String> result = new HashMap<>();

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

            if(room.getCapacity() <= tempList.size()) {
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
        if(list.contains(booking)){
            list.remove(booking);
            return room.getCapacity()> list.size();
        } else {
            return room.getCapacity() > list.size();
        }
    }

    private List<Booking> reservedBookings(Date dateLo, Date dateHi, Room room) {
        EntityManager entityManager = roomDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);
        query.select(root).where
                (builder.and(
                        builder.lessThan(root.get("bookingStartTime"), dateHi),
                        builder.greaterThan(root.get("bookingEndTime"), dateLo)),
                        builder.equal(root.get("room"), room),
                        builder.or(
                                builder.equal(root.get("bookingState"), BookingState.BOOKED),
                                builder.equal(root.get("bookingState"), BookingState.ACTIVE)));
        List<Booking> list = entityManager.createQuery(query).getResultList();
        return list;
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
}
