package ua.softserveinc.tc.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.service.BookingService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class BookingDaoTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDao bookingDao;

    @Mock
    private List<Booking> bookingList;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @Transactional
    @Rollback(true)
    public void updateRecurrentBookingsDAOTest() {
        Booking booking1 = getNewBooking();
        Long recurrentId = booking1.getRecurrentId();
        bookingDao.create(booking1);
        Assert.assertNotNull("Can't create recurrent booking",booking1.getIdBook());
        Assert.assertNotNull("Can't get recurrent booking", bookingDao.getRecurrentBookingsByRecurrentId(recurrentId).get(0));
        Booking booking2 = getNewBooking();
        booking1.setBookingState(BookingState.CANCELLED);
        booking2.setBookingStartTime(new Date());
        List<Booking> bookings1 = new ArrayList<>();
        List<Booking> bookings2 = new ArrayList<>();
        bookings1.add(booking1);
        bookings2.add(booking2);
        bookingDao.updateRecurrentBookingsDAO(bookings1,bookings2);
        Assert.assertEquals("Bad database update",booking2,bookingDao.getRecurrentBookingsByRecurrentId(booking2.getRecurrentId()).get(0));
    }

    @Test (expected = DataIntegrityViolationException.class)
    @Transactional
    @Rollback(true)
    public void updateRecurrentBookingsDAOFailTest() {
        Booking booking1 = getNewBooking();
        Long recurrentId = booking1.getRecurrentId();
        bookingDao.create(booking1);
        Assert.assertNotNull("Can't create recurrent booking",booking1.getIdBook());
        Assert.assertNotNull("Can't get recurrent booking", bookingDao.getRecurrentBookingsByRecurrentId(recurrentId).get(0));
        Booking booking2 = getNewBooking();
        booking1.setBookingState(BookingState.CANCELLED);
        booking2.setBookingStartTime(null);
        List<Booking> bookings1 = new ArrayList<>();
        List<Booking> bookings2 = new ArrayList<>();
        bookings1.add(booking1);
        bookings2.add(booking2);
        bookingDao.updateRecurrentBookingsDAO(bookings1,bookings2);
        Assert.assertEquals("Bad database update",booking2,bookingDao.getRecurrentBookingsByRecurrentId(booking2.getRecurrentId()).get(0));
    }

    private Booking getNewBooking(){
        Booking booking = bookingDao.findById(1L);
        booking.setBookingStartTime(new Date());
        booking.setBookingEndTime(new Date(booking.getBookingStartTime().getTime() + (1000 * 60 * 60 * 2)));
        booking.setBookingState(BookingState.ACTIVE);
        Long newRecID = bookingService.getMaxRecurrentId();
        booking.setRecurrentId(newRecID+1);
        return booking;
    }

}

