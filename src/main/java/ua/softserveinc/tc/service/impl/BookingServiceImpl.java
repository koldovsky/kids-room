package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.RateService;
import ua.softserveinc.tc.util.ApplicationConfigurator;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.util.DateUtil.*;

@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking> implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private RateService rateService;

    @Autowired
    private ChildService childService;

    @Autowired
    private ApplicationConfigurator appConfigurator;

    @Override
    public List<Booking> getBookings(String startDate, String endDate) {
        return getBookings(null, null, startDate, endDate);
    }

    @Override
    public List<Booking> getBookings(User user, String startDate, String endDate) {
        return getBookings(user, null, startDate, endDate);
    }

    @Override
    public List<Booking> getBookings(Room room, String startDate, String endDate) {
        return getBookings(null, room, startDate, endDate);
    }

    @Override
    public List<Booking> getBookings(User user, Room room, String startDate, String endDate) {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
        Root<Booking> root = criteria.from(Booking.class);

        List<Predicate> restrictions = new ArrayList<>(Arrays.asList(builder.equal(root.get(BookingConstants.Entity.STATE),
                BookingState.COMPLETED), builder.between(root.get(BookingConstants.Entity.START_TIME),
                toDate(startDate), toDate(endDate))));

        if (user != null) restrictions.add(builder.equal(root.get(BookingConstants.Entity.USER), user));
        if (room != null) restrictions.add(builder.equal(root.get(BookingConstants.Entity.ROOM), room));

        criteria.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
        criteria.orderBy(builder.asc(root.get(BookingConstants.Entity.START_TIME)));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public void calculateAndSetDuration(Booking booking) {
        long difference = booking.getBookingEndTime().getTime() - booking.getBookingStartTime().getTime();

        booking.setDuration(difference);
    }

    @Override
    public void calculateAndSetSum(Booking booking) {
        calculateAndSetDuration(booking);

        List<Rate> rates = booking.getIdRoom().getRates();
        Rate closestRate = rateService.calculateClosestRate(booking.getDuration(), rates);

        booking.setSum(closestRate.getPriceRate());
        bookingDao.update(booking);
    }

    @Override
    public Long getSumTotal(List<Booking> bookings) {
        return bookings.stream().mapToLong(Booking::getSum).sum();
    }

    @Override
    public Map<User, Long> generateAReport(List<Booking> bookings) {
        return bookings.stream().collect(Collectors.groupingBy(Booking::getIdUser, Collectors.summingLong(Booking::getSum)));
    }

    @Override
    public Map<Room, Long> generateStatistics(List<Booking> bookings) {
        return bookings.stream().collect(Collectors.groupingBy(Booking::getIdRoom, Collectors.summingLong(Booking::getSum)));
    }

    @Override
    public List<Booking> getBookingsWithZeroSum() {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        query.where(builder.equal(root.get(BookingConstants.Entity.SUM), 0), builder.equal(root.get(BookingConstants.Entity.STATE), BookingState.COMPLETED));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Booking> getTodayNotCancelledBookingsByRoom(Room room) {
        return bookingDao.getTodayNotCancelledBookingsByRoom(warkingHours().get(0),
                warkingHours().get(1), room);
    }

    @Override
    public List<Booking> getTodayBookingsByRoom(Room room) {
        return bookingDao.getTodayBookingsByRoom(warkingHours().get(0),
                warkingHours().get(1), room);
    }

    public Boolean checkFreePlaces(Room room, String startTime, String endTime) {

        Date startTimeDate = toDateAndTime(startTime);
        Date endTimeDate = toDateAndTime(endTime);
        List<Booking> list = bookingDao.getTodayBookingsByRoom(startTimeDate, endTimeDate, room);
        Integer countBooking = list.size();
        Integer roomCapacity = room.getCapacity();
        if (countBooking < roomCapacity) {
            return true;
        } else return false;
    }

    @Override
    public Booking confirmBookingStartTime(BookingDTO bookingDTO) {
        Booking booking = findById(bookingDTO.getId());
        Date date = replaceBookingTime(booking, bookingDTO.getStartTime());
        booking.setBookingStartTime(date);
        return booking;
    }

    @Override
    public Booking confirmBookingEndTime(BookingDTO bookingDTO) {
        Booking booking = findById(bookingDTO.getId());
        Date date = replaceBookingTime(booking, bookingDTO.getEndTime());
        booking.setBookingEndTime(date);
        return booking;
    }

    @Override
    public Date replaceBookingTime(Booking booking, String time) {
        DateFormat dfDate = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
        String dateString = dfDate.format(booking.getBookingStartTime()) + " " + time;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDateAndTime(dateString));
        return calendar.getTime();
    }

    @Override
    public List<BookingDTO> persistBookingsFromDTOandSetID(List<BookingDTO> listDTO) {

        listDTO.forEach(bookingDTO -> {
            Booking booking = bookingDTO.getBookingObject();
            booking.setBookingState(BookingState.BOOKED);
            bookingDao.create(booking);
            bookingDTO.setId(booking.getIdBook());
        });

        return listDTO;
    }

    //TODO: maybe move next three methods to RoomService
    @Override
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

    @Override
    public Map<String, String> getBlockedPeriodsForDay(Room room, Calendar calendarStart) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        //Calendar calendarStart = Calendar.getInstance();
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
            if (!isPeriodAvailable(room, startDate, endDate)) {
                blockedPeriods.put(
                        convertDateToString(startDate),
                        convertDateToString(endDate)
                );
            }
        });

        return blockedPeriods;
    }

    public Boolean isPeriodAvailable(Room room, Date dateLo, Date dateHi) {
        return !(bookingDao.getTodayBookingsByRoom(dateLo, dateHi, room)
                .stream().filter(booking ->
                        booking.getBookingState() == BookingState.BOOKED ||
                                booking.getBookingState() == BookingState.ACTIVE)
                .collect(Collectors.toList())
                .size() > room.getCapacity());
    }
}


