package ua.softserveinc.tc.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.dto.BookingDto;

import java.util.Arrays;
import java.util.Date;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.RateService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.validator.BookingValidator;
import ua.softserveinc.tc.validator.RecurrentBookingValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateUtil.class)
public class BookingServiceTest {

    @Mock
    private Logger logger;

    @Mock
    private RateService rateService;

    @Mock
    private RoomService roomService;

    @Mock
    private ChildService childService;

    @Mock
    private UserService userService;

    @Mock
    private BookingDao bookingDao;

    @Mock
    private UserDao userDao;

    @Mock
    private RoomDao roomDao;

    @Mock
    private BookingDto testBookingDto;

    @Mock
    private Booking testBooking;

    @Mock
    private Child testChild;

    @Mock
    private Room testRoom;

    @Mock
    private User testUser;

    @Mock
    private RecurrentBookingValidator recurrentBookingValidator;

    @Mock
    private BookingValidator bookingValidator;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    public void testCalculateAndSetDuration() {
        Date testDate = new Date(0);

        when(testBooking.getBookingStartTime()).thenReturn(testDate);
        when(testBooking.getBookingEndTime()).thenReturn(testDate);

        bookingService.calculateAndSetDuration(testBooking);

        verify(testBooking, times(1)).setDuration(0L);
    }

    @Test
    public void testCalculateAndSetSum() {
        Date testDate = new Date(0);

        when(testBooking.getBookingStartTime()).thenReturn(testDate);
        when(testBooking.getBookingEndTime()).thenReturn(testDate);

        bookingService.calculateAndSetSum(testBooking);

        verify(bookingDao, times(1)).update(testBooking);
    }

    @Test
    public void testGetSumTotal() {
        Booking secondTestBooking = mock(Booking.class);

        when(testBooking.getSum()).thenReturn(1L);
        when(secondTestBooking.getSum()).thenReturn(2L);

        Long returnValue =
                bookingService.getSumTotal(Arrays.asList(testBooking, secondTestBooking));

        assertEquals("The sum must be equal 3", 3L, (long)returnValue);
    }

    @Test
    public void testGenerateAReport() {

    }

    @Test
    public void testGenerateStatistics() {

    }

    @Test
    public void testConfirmBookingStartTime() {

    }

    @Test
    public void testConfirmBookingEndTime() {

    }

    @Test
    public void testReplaceBookingTime() {

    }

}
