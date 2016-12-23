package ua.softserveinc.tc.service.unitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.service.impl.BookingServiceImpl;

import java.util.Date;
import java.util.GregorianCalendar;


public class BookingServiceTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private Booking booking;

    @Mock
    private BookingDao bookingDao;


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

        Assert.assertEquals("When setting equal dates duration of booking should be zero.",
                nullDuration,
                booking.getDuration());

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

        Assert.assertEquals("When setting equal dates duration of booking should be zero.",
                nullDuration,
                booking.getDuration());
    }

}
