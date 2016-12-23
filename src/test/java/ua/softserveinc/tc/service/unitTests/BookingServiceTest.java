package ua.softserveinc.tc.service.unitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.config.TestBaseConfigClass;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.ChildDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.service.RateService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.impl.BookingServiceImpl;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by melancholiya.
 */

public class BookingServiceTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private Booking booking;

    @Mock
    private BookingDao bookingDao;

    @Mock
    private RateService rateService;

    @Mock
    private RoomService roomService;

    @Mock
    private UserDao userDao;

    @Mock
    private RoomDao roomDao;

    @Mock
    private ChildDao childDao;

    @Before
    public void beforeTests() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCalculateAndSetSum() {
        //bookingService.calculateAndSetSum(booking);
    }

    @Test
    public void testCalculateAndSetZeroDuration() {
        Mockito.when(booking.getBookingStartTime()).thenReturn(new Date());
        Mockito.when(booking.getBookingEndTime()).thenReturn(new Date());

        bookingService.calculateAndSetDuration(booking);

        Long nullDuration = 0L;

        Assert.assertEquals("When setting equal dates duration of booking should be zero."
                , nullDuration
                , booking.getDuration());


    }

    @Test
    public void testCalculateAndSetNotZeroDuration() {
        Date startDate = new GregorianCalendar(2016, 0, 20).getTime();
        Date endDate = new GregorianCalendar(2016, 0, 21).getTime();

        Booking booking = new Booking();

        booking.setBookingStartTime(startDate);
        booking.setBookingEndTime(endDate);

        bookingService.calculateAndSetDuration(booking);

        Long nullDuration = 86400000L;

        Assert.assertEquals("When setting equal dates duration of booking should be zero."
                , nullDuration
                , booking.getDuration());
    }

}

/**
 * void calculateAndSetSum(Booking booking);
 * (+) void calculateAndSetDuration(Booking booking);
 * Long getSumTotal(List<Booking> bookings);
 * <p>
 * Map<User, Long> generateAReport(List<Booking> bookings);
 * Map<Room, Long> generateStatistics(List<Booking> bookings);
 * <p>
 * List<Booking> getNotCompletedAndCancelledBookings(Date startDate, Date endDate, Room room);
 * <p>
 * List<Booking> getBookings(Date startDate, Date endDate, BookingState... bookingStates);
 * List<Booking> getBookings(Date startDate, Date endDate, User user, BookingState... bookingStates);
 * List<Booking> getBookings(Date startDate, Date endDate, Room room,
 * boolean includeOneDay, BookingState... bookingStates);
 * List<Booking> getBookings(Date startDate, Date endDate, User user, Room room,
 * boolean includeLastDay, BookingState... bookingStates);
 * <p>
 * Booking confirmBookingEndTime(BookingDto bookingDto);
 * Booking confirmBookingStartTime(BookingDto bookingDto);
 * <p>
 * Date replaceBookingTime(Booking booking, String time);
 * <p>
 * Boolean checkForDuplicateBooking(List<BookingDto> listDto);
 * Boolean checkForDuplicateBookingSingle (BookingDto bookingDto);
 * <p>
 * List<BookingDto> persistBookingsFromDtoAndSetId (List<BookingDto> listDTO);
 * List<BookingDto> getAllBookingsByUserAndRoom(Long idUser, Long idRoom);
 * <p>
 * Long getMaxRecurrentId();
 * List<BookingDto> makeRecurrentBookings(List<BookingDto> bookingDtos);
 * BookingDto getRecurrentBookingForEditingById(long bookingId);
 * List<BookingDto> updateRecurrentBookings(BookingDto recurrentBookingDtos);
 */