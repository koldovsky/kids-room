package ua.softserveinc.tc.service.impl;

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
import ua.softserveinc.tc.util.Log;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<PeriodDto> getBlockedPeriods(Room room, Calendar start, Calendar end) {
        if (start.equals(end)) {
            return getBlockedPeriodsForDay(room, start);
        }

        List<PeriodDto> result = new ArrayList<>();
        while (start.compareTo(end) <= 0) {
            result.addAll(getBlockedPeriodsForDay(room, start));
            start.add(Calendar.DAY_OF_MONTH, 1);
        }

        return result;
    }


    private List<PeriodDto> getBlockedPeriodsForDay(Room room, Calendar calendarStart) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(calendarStart.getTime());

        List<PeriodDto> result = new ArrayList<>();

        try {
            Calendar temp = Calendar.getInstance();
            temp.setTime(timeFormat.parse(room.getWorkingHoursStart()));
            calendarStart.set(Calendar.HOUR_OF_DAY, temp.get(Calendar.HOUR_OF_DAY));
            calendarStart.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));

            temp.setTime(timeFormat.parse(room.getWorkingHoursEnd()));
            calendarEnd.set(Calendar.HOUR_OF_DAY, temp.get(Calendar.HOUR_OF_DAY));
            calendarEnd.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));
        } catch (ParseException pe) {
            log.error("Could not parse date", pe);
            return result;
        }

        Calendar temp = Calendar.getInstance();

        int minPeriod = appConfigurator.getMinPeriodSize();
        while (calendarStart.compareTo(calendarEnd) <= 0) {
            temp.add(Calendar.MINUTE, minPeriod);

            Date lo = calendarStart.getTime();
            Date hi = temp.getTime();

            if(isPeriodAvailable(lo, hi, room)) {
                result.add(new PeriodDto(
                        convertDateToString(lo),
                        convertDateToString(hi)
                ));
            }
            calendarStart.setTime(temp.getTime());
        }


        return result;
    }

    @Override
    public Boolean isPeriodAvailable(Date dateLo, Date dateHi, Room room) {
        List<Booking> bookings = reservedBookings(dateLo, dateHi, room);
        return room.getCapacity() > bookings.size();
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
                        builder.greaterThan(root.get("bookingEndTime"), dateLo)), builder.equal(root.get("room"), room),
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
