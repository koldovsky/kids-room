package ua.softserveinc.tc.repo;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.categories.IntegrationTest;
import ua.softserveinc.tc.config.TestBaseConfigClass;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.messaging.BookingMessages;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;

import java.util.Calendar;
import java.util.List;

import static ua.softserveinc.tc.util.DateUtil.toDate;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class BookingRepositoryTestIT {

    @Autowired
    private BookingService bookingRepository;

    @Autowired
    private RoomService roomService;

    @DatabaseSetup(value = "classpath:bookingRepository/multiple-bookings.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:bookingRepository/multiple-bookings.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testCountByRoomAndBookingStateWhenThereIsMultipleBookings() {
        Long count = bookingRepository.countByRoomAndBookingState(roomService.findEntityById(1L), BookingState.ACTIVE);
        Assert.assertEquals(BookingMessages.COUNT_BY_ROOM_AND_BOOKING_STATE, Long.valueOf(2), count);
    }

    @DatabaseSetup(value = "classpath:bookingRepository/one-booking.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:bookingRepository/one-booking.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByBookingStateWhenThereIsOneBooking() {

        List<Booking> found = bookingRepository.findByBookingState(BookingState.ACTIVE);
        Assert.assertEquals(BookingMessages.COUNT_BY_ROOM_AND_BOOKING_STATE, 1, found.size());
    }

    @DatabaseSetup(value = "classpath:bookingRepository/no-bookings.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:bookingRepository/no-bookings.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByBookingStateWhenThereIsNoBookings() {

        List<Booking> found = bookingRepository.findByBookingState(BookingState.ACTIVE);
        Assert.assertTrue(BookingMessages.COUNT_BY_ROOM_AND_BOOKING_STATE, found.isEmpty());
    }

    @DatabaseSetup(value = "classpath:bookingRepository/multiple-bookings.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:bookingRepository/multiple-bookings.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByBookingStateWhenThereIsMultipleBookings() {

        List<Booking> active_bookings = bookingRepository.findByBookingState(BookingState.ACTIVE);
        Assert.assertEquals(BookingMessages.COUNT_BY_ROOM_AND_BOOKING_STATE, 2, active_bookings.size());

        List<Booking> blocked_bookings = bookingRepository.findByBookingState(BookingState.BOOKED);
        Assert.assertEquals(BookingMessages.COUNT_BY_ROOM_AND_BOOKING_STATE, 0, blocked_bookings.size());
    }

    @DatabaseSetup(value = "classpath:bookingRepository/multiple-bookings.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:bookingRepository/multiple-bookings.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByBookingStateAndBookingStartTimeLessThan() {

        Calendar startDate = Calendar.getInstance();
        startDate.set(2016, 7, 7, 15, 15, 0);
        System.out.println(startDate.getTime());
        List<Booking> bookings = bookingRepository
                .findByBookingStateAndBookingStartTimeLessThan(BookingState.ACTIVE, toDate(startDate));

        Assert.assertEquals(Long.valueOf(1), bookings.get(0).getIdBook());
    }

}