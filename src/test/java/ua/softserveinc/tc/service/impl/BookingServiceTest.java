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
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.RateService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.service.BaseService;
import ua.softserveinc.tc.util.BookingsCharacteristics;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.validator.BookingValidator;
import ua.softserveinc.tc.validator.RecurrentBookingValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.internal.verification.AtLeast;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DateUtil.class, BookingDto.class})
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

        verify(testBooking).setDuration(0L);
    }

    @Test
    public void testCalculateAndSetSum() {
        Date testDate = new Date(0);

        when(testBooking.getBookingStartTime()).thenReturn(testDate);
        when(testBooking.getBookingEndTime()).thenReturn(testDate);
        when(rateService.calculateBookingCost(any())).thenReturn(1L);

        bookingService.calculateAndSetSum(testBooking);

        verify(bookingDao).update(testBooking);
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

    @Test
    public void testGetRecurrentBookingForEditingByIdWhenNotExist() {
        when(bookingDao.getRecurrentBookingsByRecurrentId(any()))
                .thenReturn(Collections.emptyList());

        assertEquals("The object must be null", null,
                bookingService.getRecurrentBookingForEditingById(0L));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetRecurrentBookingForEditingByIdWhenExist() {
        mockStatic(BookingDto.class);
        Booking secondTestBooking = mock(Booking.class);
        Calendar workCalendar = Calendar.getInstance();
        List<Booking> bookings = Arrays.asList(testBooking, secondTestBooking);

        workCalendar.set(2019, 0, 1, 12, 0, 0); //2019-01-01T12:00:00 Tuesday(3)
        Date startDateOfRecurrentPeriod = workCalendar.getTime();

        workCalendar.set(2019, 0, 4, 12, 0, 0); //2019-01-04T12:00:00 Friday(6)
        Date endDateOfRecurrentPeriod = workCalendar.getTime();

        ArgumentCaptor<Set> setWeekOfDays =
                ArgumentCaptor.forClass(Set.class);

        when(testBooking.getBookingStartTime()).thenReturn(startDateOfRecurrentPeriod);
        when(secondTestBooking.getBookingStartTime()).thenReturn(endDateOfRecurrentPeriod);
        when(bookingDao.getRecurrentBookingsByRecurrentId(any()))
                .thenReturn(bookings);

        when(BookingDto.getRecurrentBookingDto(eq(bookings), setWeekOfDays.capture()))
                .thenReturn(testBookingDto);

        assertEquals("The object must be testBookingDto", testBookingDto,
                bookingService.getRecurrentBookingForEditingById(0L));

        assertEquals("The object must be equals to 2", 2,
                setWeekOfDays.getValue().size());

        assertTrue("The sets must be equals",
                setWeekOfDays.getValue().contains(Calendar.TUESDAY)
                        && setWeekOfDays.getValue().contains(Calendar.FRIDAY));
    }

    @Test
    public void testGetAllBookingsByUserAndRoom() throws Exception {
        Booking secondTestBooking = mock(Booking.class);
        List<Booking> listOfBookings = Arrays.asList(testBooking, secondTestBooking);

        ArgumentCaptor<BookingsCharacteristics> characteristicsCaptor =
                ArgumentCaptor.forClass(BookingsCharacteristics.class);

        when(userDao.findById(anyLong())).thenReturn(testUser);
        when(roomDao.findById(anyLong())).thenReturn(testRoom);
        when(bookingDao.getBookings(characteristicsCaptor.capture())).thenReturn(listOfBookings);

        List<BookingDto> listOfBookingsDto = bookingService.getAllBookingsByUserAndRoom(1L, 1L);

        BookingsCharacteristics characteristics = characteristicsCaptor.getValue();
        assertEquals("The object must be equals to 2", 2, listOfBookingsDto.size());

        assertEquals("The size must be 1", 1,
                characteristics.getUsers().size());
        assertEquals("The objects must be equals", testUser,
                characteristics.getUsers().get(0));
        assertEquals("The size must be 1", 1,
                characteristics.getRooms().size());
        assertEquals("The objects must be equals", testRoom,
                characteristics.getRooms().get(0));
        assertEquals("The size must be 1", 1,
                characteristics.getBookingsStates().size());
        assertEquals("The objects must be equals", BookingState.BOOKED,
                characteristics.getBookingsStates().get(0));
        assertEquals("The Date must be null", null,
                characteristics.getStartDateOfBookings());
        assertEquals("The Date must be null", null,
                characteristics.getEndDateOfBookings());

        checkEmptyPartOfBookingCharacteristics(characteristics);
    }

    @Test
    public void testGetNotCompletedAndCancelledBookings() {
        Calendar workCalendar = Calendar.getInstance();

        workCalendar.set(2019, 0, 1, 12, 0, 0); //2019-01-01T12:00:00 Tuesday(3)
        Date startTestDate = workCalendar.getTime();

        workCalendar.set(2019, 0, 4, 12, 0, 0); //2019-01-04T12:00:00 Friday(6)
        Date endTestDate = workCalendar.getTime();

        ArgumentCaptor<BookingsCharacteristics> characteristicsCaptor =
                ArgumentCaptor.forClass(BookingsCharacteristics.class);

        when(bookingDao.getBookings(characteristicsCaptor.capture()))
                .thenReturn(Collections.emptyList());

        bookingService.getNotCompletedAndCancelledBookings(startTestDate, endTestDate, testRoom);

        BookingsCharacteristics characteristics = characteristicsCaptor.getValue();
        assertEquals("The size must be 1", 1,
                characteristics.getRooms().size());
        assertEquals("The objects must be equals", testRoom,
                characteristics.getRooms().get(0));
        assertEquals("The size must be 2", 2,
                characteristics.getBookingsStates().size());
        assertEquals("The objects must be equals", BookingState.ACTIVE,
                characteristics.getBookingsStates().get(0));
        assertEquals("The objects must be equals", BookingState.BOOKED,
                characteristics.getBookingsStates().get(1));
        assertEquals("The size must be 0", 0,
                characteristics.getUsers().size());

        checkDatesPartOfBookingCharacteristics(characteristics, startTestDate, endTestDate);
        checkEmptyPartOfBookingCharacteristics(characteristics);

    }

    @Test
    public void testGetDatesOfReservedBookingsWithDatesRoom() {
        Calendar workCalendar = Calendar.getInstance();

        workCalendar.set(2019, 0, 1, 12, 0, 0); //2019-01-01T12:00:00 Tuesday(3)
        Date testDate = workCalendar.getTime();

        bookingService.getDatesOfReservedBookings(testDate, testDate, testRoom);

        verify(bookingDao).getDatesOfReservedBookings(testDate, testDate, testRoom);

    }

    @Test
    public void testGetDatesOfReservedBookingsWithBookingCharacteristics() {
        BookingsCharacteristics characteristics = new BookingsCharacteristics.Builder().build();

        bookingService.getDatesOfReservedBookings(characteristics);

        verify(bookingDao).getDatesOfReservedBookings(characteristics);

    }

    @Test
    public void testGetBookingsWithDatesAndBookingsStates() {
        Calendar workCalendar = Calendar.getInstance();

        workCalendar.set(2019, 0, 1, 12, 0, 0); //2019-01-01T12:00:00 Tuesday(3)
        Date startTestDate = workCalendar.getTime();

        workCalendar.set(2019, 0, 4, 12, 0, 0); //2019-01-04T12:00:00 Friday(6)
        Date endTestDate = workCalendar.getTime();

        ArgumentCaptor<BookingsCharacteristics> characteristicsCaptor =
                ArgumentCaptor.forClass(BookingsCharacteristics.class);

        when(bookingDao.getBookings(characteristicsCaptor.capture()))
                .thenReturn(Collections.emptyList());

        bookingService.getBookings(startTestDate, endTestDate, BookingState.CANCELLED,
                BookingState.COMPLETED);

        BookingsCharacteristics characteristics = characteristicsCaptor.getValue();
        assertEquals("The size must be 2", 2,
                characteristics.getBookingsStates().size());
        assertEquals("The objects must be equals", BookingState.CANCELLED,
                characteristics.getBookingsStates().get(0));
        assertEquals("The objects must be equals", BookingState.COMPLETED,
                characteristics.getBookingsStates().get(1));
        assertEquals("The size must be 0", 0,
                characteristics.getUsers().size());
        assertEquals("The size must be 0", 0,
                characteristics.getRooms().size());

        checkDatesPartOfBookingCharacteristics(characteristics, startTestDate, endTestDate);
        checkEmptyPartOfBookingCharacteristics(characteristics);
    }

    @Test
    public void testGetBookingsWithDatesUserAndBookingsStates() {
        Calendar workCalendar = Calendar.getInstance();

        workCalendar.set(2019, 0, 1, 12, 0, 0); //2019-01-01T12:00:00 Tuesday(3)
        Date startTestDate = workCalendar.getTime();

        workCalendar.set(2019, 0, 4, 12, 0, 0); //2019-01-04T12:00:00 Friday(6)
        Date endTestDate = workCalendar.getTime();

        ArgumentCaptor<BookingsCharacteristics> characteristicsCaptor =
                ArgumentCaptor.forClass(BookingsCharacteristics.class);

        when(bookingDao.getBookings(characteristicsCaptor.capture()))
                .thenReturn(Collections.emptyList());

        bookingService.getBookings(new Date[] {startTestDate, endTestDate}, testUser,
                BookingState.ACTIVE, BookingState.CALCULATE_SUM);

        BookingsCharacteristics characteristics = characteristicsCaptor.getValue();
        assertEquals("The size must be 2", 2,
                characteristics.getBookingsStates().size());
        assertEquals("The objects must be equals", BookingState.ACTIVE,
                characteristics.getBookingsStates().get(0));
        assertEquals("The objects must be equals", BookingState.CALCULATE_SUM,
                characteristics.getBookingsStates().get(1));
        assertEquals("The size must be 0", 1,
                characteristics.getUsers().size());
        assertEquals("The objects must be equals", testUser,
                characteristics.getUsers().get(0));
        assertEquals("The size must be 0", 0,
                characteristics.getRooms().size());

        checkDatesPartOfBookingCharacteristics(characteristics, startTestDate, endTestDate);
        checkEmptyPartOfBookingCharacteristics(characteristics);

    }

    @Test
    public void testGetBookingsWithBookingsCharacteristics() {
        BookingsCharacteristics characteristics = new BookingsCharacteristics.Builder().build();

        bookingService.getBookings(characteristics);

        verify(bookingDao).getBookings(characteristics);
    }

    @Test
    public void testHasDuplicateBookingsForBookingCharacteristicsCorrectnessNull() {
        ArgumentCaptor<BookingsCharacteristics> characteristicsCaptor =
                ArgumentCaptor.forClass(BookingsCharacteristics.class);

        when(testBookingDto.getStartTime()).thenReturn("");
        when(testBookingDto.getEndTime()).thenReturn("");
        when(roomService.findEntityById(any())).thenReturn(testRoom);
        when(userService.findEntityById(any())).thenReturn(testUser);
        when(childService.findEntityById(any())).thenReturn(testChild);
        when(testBookingDto.getId()).thenReturn(null);
        when(testBookingDto.getRecurrentId()).thenReturn(null);
        when(bookingDao.getDuplicateBookings(characteristicsCaptor.capture()))
                .thenReturn(Collections.emptyList());

        assertFalse("Must be false",
                bookingService.hasDuplicateBookings(Collections.singletonList(testBookingDto)));

        assertEquals("Must be equals",
                new BookingsCharacteristics.Builder().build(), characteristicsCaptor.getValue());

    }

    @Test
    @SuppressWarnings({"unchecked", "varargs"})
    public void testHasDuplicateBookingsForBookingCharacteristicsCorrectnessNotNull() {
        BookingDto secondTestBookingDto = mock(BookingDto.class);
        Child secondTestChild = mock(Child.class);
        ArgumentCaptor<BookingsCharacteristics> characteristicsCaptor =
                ArgumentCaptor.forClass(BookingsCharacteristics.class);

        Calendar workCalendar = Calendar.getInstance();
        workCalendar.clear();

        workCalendar.set(2019, 0, 7, 12, 0, 0); //2019-01-26T12:00:00 Second day when condition
        Date startLastDate = workCalendar.getTime();

        workCalendar.set(2019, 0, 7, 13, 0, 0); //2019-01-26T13:00:00 Second day when condition
        Date endLastDate = workCalendar.getTime();

        when(testBookingDto.getStartTime()).thenReturn("2019-01-01T12:00:00");
        when(testBookingDto.getEndTime()).thenReturn("2019-02-01T13:00:00");
        when(testBookingDto.getDaysOfWeek()).thenReturn("Mon Tue");
        when(secondTestBookingDto.getStartTime()).thenReturn("2019-01-01T12:00:00");
        when(secondTestBookingDto.getEndTime()).thenReturn("2019-02-01T12:00:00");
        when(roomService.findEntityById(any())).thenReturn(testRoom);
        when(userService.findEntityById(any())).thenReturn(testUser);
        when(childService.findEntityById(any())).thenReturn(testChild);
        when(testBookingDto.getChild()).thenReturn(testChild);
        when(testBookingDto.getId()).thenReturn(1L);
        when(testBookingDto.getRecurrentId()).thenReturn(1L);
        when(secondTestBookingDto.getChild()).thenReturn(secondTestChild);
        when(secondTestBookingDto.getId()).thenReturn(2L);
        when(secondTestBookingDto.getRecurrentId()).thenReturn(2L);
        when(bookingDao.getDuplicateBookings(any()))
                .thenReturn(Collections.emptyList(), Collections.singletonList(null));

        assertTrue("Must be true", bookingService.hasDuplicateBookings(
                Arrays.asList(testBookingDto, secondTestBookingDto)));
        verify(bookingDao, times(2)).getDuplicateBookings(characteristicsCaptor.capture());
        BookingsCharacteristics characteristics = characteristicsCaptor.getValue();
        assertEquals("Must size must be 2", 2, characteristics.getChildren().size());
        assertEquals("Must be equals", testChild, characteristics.getChildren().get(0));
        assertEquals("Must be equals", secondTestChild, characteristics.getChildren().get(1));
        assertEquals("Must size must be 2", 2, characteristics.getIdsOfBookings().size());
        assertEquals("Must be equals to 2", 1L, (long)characteristics.getIdsOfBookings().get(0));
        assertEquals("Must be equals to 2", 2L, (long)characteristics.getIdsOfBookings().get(1));
        assertEquals("Must size must be 2", 2, characteristics.getRecurrentIdsOfBookings().size());
        assertEquals("Must be equals to 2", 1L,
                (long)characteristics.getRecurrentIdsOfBookings().get(0));
        assertEquals("Must be equals to 2", 2L,
                (long)characteristics.getRecurrentIdsOfBookings().get(1));
        assertEquals("The dates are not correct", startLastDate,
                characteristics.getStartDateOfBookings());
        assertEquals("The dates are not correct", endLastDate,
                characteristics.getEndDateOfBookings());
        assertEquals("The size must be 0", 0,
                characteristics.getUsers().size());
        assertEquals("The size must be 0", 0,
                characteristics.getRooms().size());
        assertEquals("The size must be 0", 0,
                characteristics.getBookingsStates().size());

    }

    @Test
    public void testHasDuplicateBookingsDatePartWithDaysOfWeekNull() {
        Calendar workCalendar = Calendar.getInstance();
        workCalendar.clear();

        workCalendar.set(2019, 0, 7, 12, 0, 0); //2019-01-07T12:00:00 Second day when condition
        Date startLastDate = workCalendar.getTime();

        workCalendar.set(2019, 0, 7, 13, 0, 0); //2019-01-07T13:00:00 Second day when condition
        Date endLastDate = workCalendar.getTime();

        ArgumentCaptor<BookingsCharacteristics> characteristicsCaptor =
                ArgumentCaptor.forClass(BookingsCharacteristics.class);

        when(roomService.findEntityById(anyLong())).thenReturn(testRoom);
        when(userService.findEntityById(anyLong())).thenReturn(testUser);
        when(childService.findEntityById(anyLong())).thenReturn(testChild);
        when(testBookingDto.getDateStartTime()).thenReturn(startLastDate);
        when(testBookingDto.getDateEndTime()).thenReturn(endLastDate);
        when(testBookingDto.getDaysOfWeek()).thenReturn(null);
        when(bookingDao.getDuplicateBookings(any()))
                .thenReturn(Collections.emptyList());

        assertFalse("Must be false", bookingService.hasDuplicateBookings(
                Collections.singletonList(testBookingDto)));
        verify(bookingDao).getDuplicateBookings(characteristicsCaptor.capture());

        BookingsCharacteristics characteristics = characteristicsCaptor.getValue();

        assertEquals("The dates are not correct", startLastDate,
                characteristics.getStartDateOfBookings());
        assertEquals("The dates are not correct", endLastDate,
                characteristics.getEndDateOfBookings());


    }

    @Test
    public void testHasDuplicateBookingsDatePartBadNumberFormat() {
        Date testDate = new Date();
        mockStatic(DateUtil.class);

        when(roomService.findEntityById(anyLong())).thenReturn(testRoom);
        when(userService.findEntityById(anyLong())).thenReturn(testUser);
        when(childService.findEntityById(anyLong())).thenReturn(testChild);
        when(testBookingDto.getDateStartTime()).thenReturn(testDate);
        when(testBookingDto.getDateEndTime()).thenReturn(testDate);
        when(testBookingDto.getDaysOfWeek()).thenReturn("Mon Tue");
        when(testBookingDto.getStartTime()).thenReturn("2019-01-01T12:00:00");
        when(testBookingDto.getEndTime()).thenReturn("2019-01-01T12:0x:00");
        when(DateUtil.toDateISOFormat(anyString())).thenReturn(testDate);
        when(DateUtil.getIntDaysOfWeek(any())).thenReturn(new int[]{0});
        when(bookingDao.getDuplicateBookings(any()))
                .thenReturn(Collections.emptyList());

        assertFalse("Must be false", bookingService.hasDuplicateBookings(
                Collections.singletonList(testBookingDto)));

        verify(logger).error(anyString(), any(Throwable.class));
    }

    @Test
    public void testHasDuplicateBookingsDatePartWithContinueAndBrake() {
        Calendar workCalendar = Calendar.getInstance();
        workCalendar.clear();

        workCalendar.set(2019, 0, 25, 12, 0, 0); //2019-01-25T12:00:00 Friday
        Date startLastDate = workCalendar.getTime();

        workCalendar.set(2019, 0, 25, 13, 0, 0); //2019-01-25T13:00:00 Friday
        Date endLastDate = workCalendar.getTime();
        mockStatic(DateUtil.class);

        when(roomService.findEntityById(anyLong())).thenReturn(testRoom);
        when(userService.findEntityById(anyLong())).thenReturn(testUser);
        when(childService.findEntityById(anyLong())).thenReturn(testChild);
        when(testBookingDto.getDateStartTime()).thenReturn(startLastDate);
        when(testBookingDto.getDateEndTime()).thenReturn(endLastDate);
        when(testBookingDto.getDaysOfWeek()).thenReturn("Mon Sat");
        when(testBookingDto.getStartTime()).thenReturn("2019-01-25T12:00:00");
        when(testBookingDto.getEndTime()).thenReturn("2019-01-25T13:00:00");
        when(DateUtil.toDateISOFormat("2019-01-25T12:00:00")).thenReturn(startLastDate);
        when(DateUtil.toDateISOFormat("2019-01-25T13:00:00")).thenReturn(endLastDate);
        when(DateUtil.getIntDaysOfWeek(any())).thenReturn(new int[]{2, 7});

        assertFalse("Must be false", bookingService.hasDuplicateBookings(
                Collections.singletonList(testBookingDto)));

    }

    @Test
    public void testHasAvailablePlacesInTheRoom() {
        BookingServiceImpl spy = spy(bookingService);
        BookingsCharacteristics characteristic = mock(BookingsCharacteristics.class);

        doReturn(Collections.emptyList())
                .when(spy).getNotAvailablePlacesTimePeriods(characteristic, 3, true);
        assertTrue("Must be true", spy.hasAvailablePlacesInTheRoom(
                characteristic, 3));

        doReturn(Collections.singletonList(null))
                .when(spy).getNotAvailablePlacesTimePeriods(characteristic, 3, true);
        assertFalse("Must be false", spy.hasAvailablePlacesInTheRoom(
                characteristic, 3));
    }

    @Test
    public void testGetAllNotAvailablePlacesTimePeriodsRoom() {
        BookingServiceImpl spy = spy(bookingService);
        ArgumentCaptor<BookingsCharacteristics> characteristicsCaptor =
                ArgumentCaptor.forClass(BookingsCharacteristics.class);

        doReturn(Collections.emptyList()).when(spy)
                .getNotAvailablePlacesTimePeriods(any(), eq(1), eq(false));

        assertEquals("The size must be 0", 0,
               spy.getAllNotAvailablePlacesTimePeriods(testRoom).size());

        verify(spy).getNotAvailablePlacesTimePeriods(
                characteristicsCaptor.capture(), eq(1), eq(false));

        BookingsCharacteristics characteristics = characteristicsCaptor.getValue();
        checkEmptyPartOfBookingCharacteristics(characteristics);
        assertEquals("The size must be 0", 0,
                characteristics.getUsers().size());
        assertEquals("The size must be 1", 1,
                characteristics.getRooms().size());
        assertEquals("Must be equals", testRoom,
                characteristics.getRooms().get(0));
        assertTrue("Must be true",
                characteristics.getStartDateOfBookings() != null);
        assertTrue("Must be true",
                characteristics.getEndDateOfBookings() != null);

    }

    @Test
    public void testGetNotAvailablePlacesTimePeriodsOnlyFirstPass() {

    }

    @Test
    public void testGetNotAvailablePlacesTimePeriodsOnlyFirstFail() {

    }

    @Test
    public void testGetNotAvailablePlacesTimePeriodsPass() {

    }

    @Test
    public void testGetNotAvailablePlacesTimePeriodsFail() {

    }

    @Test
    public void testGetNotAvailablePlacesTimePeriodsNullReservedBookings() {

    }

    private void checkEmptyPartOfBookingCharacteristics(BookingsCharacteristics characteristics) {
        assertEquals("The size must be 0", 0,
                characteristics.getChildren().size());
        assertEquals("The size must be 0", 0,
                characteristics.getIdsOfBookings().size());
        assertEquals("The size must be 0", 0,
                characteristics.getRecurrentIdsOfBookings().size());
    }

    private void checkDatesPartOfBookingCharacteristics(BookingsCharacteristics characteristics,
                                                        Date startTestDate, Date endTestDate) {
        assertEquals("The dates must be equals", startTestDate,
                characteristics.getStartDateOfBookings());
        assertEquals("The dates must be equals", endTestDate,
                characteristics.getEndDateOfBookings());
    }
}
