package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RateService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking> implements BookingService {
    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private RateService rateService;

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate) {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
        Root<Booking> root = criteria.from(Booking.class);

        criteria.where(builder.between(root.get(BookingConstants.Entity.START_TIME), startDate, endDate));
        criteria.orderBy(builder.asc(root.get(BookingConstants.Entity.START_TIME)));

        return entityManager.createQuery(criteria).getResultList();
    }

    //works bad, tested manually
    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, User user) {
        return getBookings(startDate, endDate).stream()
                .filter(booking -> booking.getIdUser().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, Room room) {
        return getBookings(startDate, endDate).stream()
                .filter(booking -> booking.getIdRoom().equals(room))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, User user, Room room) {
        return getBookings(startDate, endDate).stream()
                .filter(booking ->
                        booking.getIdUser().equals(user) && booking.getIdRoom().equals(room))
                .collect(Collectors.toList());
    }

    // Option 2
    /*@Override
    public List<Booking> getBookings(Date startDate, Date endDate) {
        return getBookings(startDate, endDate, null, null);
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, User user) {
        return getBookings(startDate, endDate, user, null);
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, Room room) {
        return getBookings(startDate, endDate, null, room);
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, User user, Room room) {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
        Root<Booking> root = criteria.from(Booking.class);

        List<Predicate> restrictions = new ArrayList<>();
        restrictions.add(builder.between(root.get(
                BookingConstants.Entity.START_TIME), startDate, endDate));

        if (user != null)
            restrictions.add(builder.equal(root.get(BookingConstants.Entity.USER), user));
        if (room != null)
            restrictions.add(builder.equal(root.get(BookingConstants.Entity.ROOM), room));

        criteria.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
        criteria.orderBy(builder.asc(root.get(BookingConstants.Entity.START_TIME)));

        return entityManager.createQuery(criteria).getResultList();
    }*/

    @Override
    public List<Booking> filterByState(List<Booking> bookings, BookingState bookingState) {
        return bookings.stream()
                .filter(booking ->
                        booking.getBookingState() == bookingState)
                .collect(Collectors.toList());
    }

    public List<Booking> filterByStates(List<Booking> bookings, BookingState... bookingStates) {
        if (bookingStates.length == 0) {
            return bookings;
        }
        return Arrays.stream(bookingStates)
                .flatMap(bookingState -> filterByState(bookings, bookingState).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> filterBySum(List<Booking> bookings, Long sum) {
        return bookings.stream()
                .filter(booking -> booking.getSum().equals(sum))
                .collect(Collectors.toList());
    }

    @Override
    public void calculateAndSetDuration(Booking booking) {
        long difference = booking.getBookingEndTime().getTime() -
                booking.getBookingStartTime().getTime();

        booking.setDuration(difference);
    }

    @Override
    public void calculateAndSetSum(Booking booking) {
        calculateAndSetDuration(booking);

        List<Rate> rates = booking.getIdRoom().getRates();
        Rate closestRate = rateService.calculateAppropriateRate(booking.getDuration(), rates);

        booking.setSum(closestRate.getPriceRate());
        bookingDao.update(booking);
    }

    @Override
    public Long getSumTotal(List<Booking> bookings) {
        return bookings.stream()
                .mapToLong(Booking::getSum)
                .sum();
    }

    @Override
    public Map<User, Long> generateAReport(List<Booking> bookings) {
        return bookings.stream()
                .collect(Collectors.groupingBy(Booking::getIdUser,
                        Collectors.summingLong(Booking::getSum)));
    }

    @Override
    public Map<Room, Long> generateStatistics(List<Booking> bookings) {
        return bookings.stream()
                .collect(Collectors.groupingBy(Booking::getIdRoom,
                        Collectors.summingLong(Booking::getSum)));
    }

    @Override
    public Booking confirmBookingStartTime(BookingDto bookingDto) {
        Booking booking = findById(bookingDto.getId());
        Date date = replaceBookingTime(booking, bookingDto.getStartTime());
        booking.setBookingStartTime(date);
        resetSumAndDuration(booking);
        return booking;
    }

    @Override
    public Booking confirmBookingEndTime(BookingDto bookingDto) {
        Booking booking = findById(bookingDto.getId());
        Date date = replaceBookingTime(booking, bookingDto.getEndTime());
        booking.setBookingEndTime(date);
        resetSumAndDuration(booking);
        return booking;
    }

    @Override
    public Booking confirmBooking(BookingDto bookingDto) {
        Booking booking = findById(bookingDto.getId());
        booking.setBookingStartTime(replaceBookingTime(booking, bookingDto.getStartTime()));
        booking.setBookingStartTime(replaceBookingTime(booking, bookingDto.getEndTime()));
        resetSumAndDuration(booking);
        return booking;
    }

    private void resetSumAndDuration(Booking booking) {
        booking.setDuration(0L);
        booking.setSum(0L);
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
    public List<BookingDto> persistBookingsFromDtoAndSetId(List<BookingDto> listDTO) {

        listDTO.forEach(bookingDTO -> {
            Booking booking = bookingDTO.getBookingObject();
            booking.setBookingState(BookingState.BOOKED);
            bookingDao.create(booking);
            bookingDTO.setId(booking.getIdBook());
        });

        return listDTO;
    }

    public List<BookingDto> getAllBookingsByUserAndRoom(Long idUser, Long idRoom) {

        List<Booking> bookings = bookingDao.findAll();

        List<BookingDto> bookingDtos = new LinkedList<>();

        for (Booking booking : bookings) {
            if ((booking.getIdRoom().getId() == idUser) && (booking.getIdUser().getId() == idRoom)) {
                bookingDtos.add(new BookingDto(booking));
            }
        }
        return bookingDtos;
    }
}

