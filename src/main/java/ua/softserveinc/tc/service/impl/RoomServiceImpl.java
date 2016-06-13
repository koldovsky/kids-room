package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.ApplicationConfigurator;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static ua.softserveinc.tc.util.DateUtil.convertDateToString;

@Service
public class RoomServiceImpl extends BaseServiceImpl<Room> implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ApplicationConfigurator appConfigurator;

    @Autowired
    private BookingService bookingService;

    @Override
    public List<Room> findByManager(User manager) {
        return roomDao.findByManager(manager);
    }

    @Override
    public void create(Room room) {
        roomDao.saveOrUpdate(room);
    }

    @Override
    public List<Room> findAll() {
        return roomDao.findAll();
    }

    @Override
    public Room update(Room entity) {
        return roomDao.update(entity);
    }

    @Override
    public void saveOrUpdate(Room room) {
        roomDao.saveOrUpdate(room);
    }

    @Override
    public List<Event> getAllEventsInRoom(Room room) {
        EntityManager entityManager = roomDao.getEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        query.select(root).where(builder.equal(root.get(EventConstants.Entity.ID_ROOM), room.getId()));

        return entityManager
                .createQuery(query)
                .getResultList();
    }

    @Override
    public Map<String, String> getBlockedPeriods(Room room, Calendar start, Calendar end) {
        if (start.equals(end)) {
            return getBlockedPeriodsForDay(room, start);
        }

        Map<String, String> blockedPeriods = new HashMap<>();
        while (start.compareTo(end) <= 0) {
            blockedPeriods.putAll(getBlockedPeriodsForDay(room, start));
            start.add(Calendar.DAY_OF_MONTH, 1);
        }

        return blockedPeriods;
    }

    /**
     * @deprecated
     */

    @Override
    @Deprecated
    public Map<String, String> getBlockedPeriodsForWeek(Room room) {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.clear(Calendar.MINUTE);
        start.clear(Calendar.SECOND);
        start.clear(Calendar.MILLISECOND);
        start.set(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek());

        Map<String, String> blockedPeriods = getBlockedPeriodsForDay(room, start);
        for (int i = 1; i < 7; i++) {
            start.add(Calendar.DAY_OF_WEEK, 1);
            blockedPeriods.putAll(getBlockedPeriodsForDay(room, start));
        }

        return blockedPeriods;
    }

    private Map<String, String> getBlockedPeriodsForDay(Room room, Calendar calendarStart) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(calendarStart.getTime());

        try {
            Calendar temp = Calendar.getInstance();
            temp.setTime(timeFormat.parse(room.getWorkingHoursStart()));
            calendarStart.set(Calendar.HOUR_OF_DAY, temp.get(Calendar.HOUR_OF_DAY));
            calendarStart.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));

            temp.setTime(timeFormat.parse(room.getWorkingHoursEnd()));
            calendarEnd.set(Calendar.HOUR_OF_DAY, temp.get(Calendar.HOUR_OF_DAY));
            calendarEnd.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));
        } catch (ParseException pe) {
            //TODO: throw reasonable exception to Advice
        }

        Calendar temp = Calendar.getInstance();
        Map<Date, Date> periods = new HashMap<>();

        while (calendarStart.compareTo(calendarEnd) <= 0) {
            temp.setTime(calendarStart.getTime());
            temp.add(Calendar.MINUTE, appConfigurator.getMinPeriodSize());
            periods.put(calendarStart.getTime(), temp.getTime());
            calendarStart = temp;
        }

        Map<String, String> blockedPeriods = new HashMap<>();
        periods.forEach((startDate, endDate) -> {
            if (!isPeriodAvailable(startDate, endDate, room)) {
                blockedPeriods.put(
                        convertDateToString(startDate),
                        convertDateToString(endDate)
                );
            }
        });

        return blockedPeriods;
    }

    @Override
    public Boolean isPeriodAvailable(Date dateLo, Date dateHi, Room room) {
        List<Booking> bookings = bookingService.getBookings(dateLo, dateHi, room, BookingConstants.States.getActiveAndBooked());
        return room.getCapacity() > bookings.size();
    }

    /**
     * @param dateLo start of period
     * @param dateHi end of period
     * @param room   a requested room
     * @return number of places available in the room for the period
     */
    public Integer getAvailableSpaceForPeriod(Date dateLo, Date dateHi, Room room) {
        List<Booking> bookings = bookingService.getBookings(dateLo, dateHi, room, BookingConstants.States.getActiveAndBooked());
        return room.getCapacity() - bookings.size();
    }
}
