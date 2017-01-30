package ua.softserveinc.tc.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
import java.util.Map;
import java.util.Calendar;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.RateService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.service.BaseService;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.validator.BookingValidator;
import ua.softserveinc.tc.validator.RecurrentBookingValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.reflect.Whitebox.getInternalState;

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
    private UserDao baseDao;

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
        Booking secondTestBooking = mock(Booking.class);
        User secondTestUser = mock(User.class);

        when(testBooking.getUser()).thenReturn(testUser);
        when(testBooking.getSum()).thenReturn(1L);
        when(secondTestBooking.getUser()).thenReturn(secondTestUser);
        when(secondTestBooking.getSum()).thenReturn(2L);

        Map<User,Long> returnValue =
                bookingService.generateAReport(Arrays.asList(testBooking, secondTestBooking));

        assertEquals("The size must be equal 2", 2, returnValue.size());
        assertEquals("The key and value are inconsistent", 1L, (long)returnValue.get(testUser));
        assertEquals("The key & value are inconsistent", 2L, (long)returnValue.get(secondTestUser));

    }

    @Test
    public void testGenerateStatistics() {
        Booking secondTestBooking = mock(Booking.class);
        Room secondTestRoom = mock(Room.class);

        when(testBooking.getRoom()).thenReturn(testRoom);
        when(testBooking.getSum()).thenReturn(2L);
        when(secondTestBooking.getRoom()).thenReturn(secondTestRoom);
        when(secondTestBooking.getSum()).thenReturn(3L);

        Map<Room,Long> returnValue =
                bookingService.generateStatistics(Arrays.asList(testBooking, secondTestBooking));

        assertEquals("The size must be equal 2", 2, returnValue.size());
        assertEquals("The key and value are inconsistent", 2L, (long)returnValue.get(testRoom));
        assertEquals("The key & value are inconsistent", 3L, (long)returnValue.get(secondTestRoom));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testConfirmBookingStartTimeThrowException() {
        BookingServiceImpl spy = spy(bookingService);

        doThrow(ResourceNotFoundException.class).when(spy).findByIdTransactional(any());

        spy.confirmBookingStartTime(testBookingDto);
    }

    @Test
    public void testConfirmBookingStartTime() {
        BookingServiceImpl spy = spy(bookingService);

        doReturn(testBooking).when(spy).findByIdTransactional(any());
        doReturn(new Date()).when(spy).replaceBookingTime(eq(testBooking), any());

        spy.confirmBookingStartTime(testBookingDto);

        assertEquals("The object must be equal", testBooking,
                spy.confirmBookingStartTime(testBookingDto));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testConfirmBookingEndTimeThrowException() {
        BookingServiceImpl spy = spy(bookingService);

        doThrow(ResourceNotFoundException.class).when(spy).findByIdTransactional(any());

        spy.confirmBookingEndTime(testBookingDto);
    }

    @Test
    public void testConfirmBookingEndTime() {
        BookingServiceImpl spy = spy(bookingService);

        doReturn(testBooking).when(spy).findByIdTransactional(any());
        doReturn(new Date()).when(spy).replaceBookingTime(eq(testBooking), any());
        doNothing().when(spy).calculateAndSetSum(testBooking);

        spy.confirmBookingEndTime(testBookingDto);

        assertEquals("The object must be equal", testBooking,
                spy.confirmBookingStartTime(testBookingDto));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testReplaceBookingTimeTrowException() {
        ResourceNotFoundException thrownException = mock(ResourceNotFoundException.class);
        mockStatic(DateUtil.class);

        when(testBooking.getBookingStartTime()).thenReturn(new Date());
        when(DateUtil.toDateAndTime(any())).thenThrow(thrownException);

        bookingService.replaceBookingTime(testBooking, "10:30");
    }

    @Test
    public void testReplaceBookingTime() {
        mockStatic(DateUtil.class);
        String testTime = "13:30";
        String expectedString = "2019-01-01 13:30";
        Calendar workCalendar = Calendar.getInstance();

        workCalendar.set(2019, 0, 1, 12, 0, 0); //2019-01-01T12:00:00
        Date dateBeforeReplacing = workCalendar.getTime();

        workCalendar.set(2019, 0, 1, 13, 30, 0); //2019-01-01T13:30:00
        Date dateAfterReplacing = workCalendar.getTime();

        ArgumentCaptor<String> dateString =
                ArgumentCaptor.forClass(String.class);

        when(testBooking.getBookingStartTime()).thenReturn(dateBeforeReplacing);
        when(DateUtil.toDateAndTime(dateString.capture())).thenReturn(dateAfterReplacing);

        assertEquals("The object must be equal", dateAfterReplacing,
                bookingService.replaceBookingTime(testBooking, testTime));

        assertEquals("The object must be equal", expectedString,
                dateString.getValue());

    }

}
