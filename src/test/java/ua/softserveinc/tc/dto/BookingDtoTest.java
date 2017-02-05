package ua.softserveinc.tc.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DateUtil.class, BookingDto.class})
public class BookingDtoTest {

    @Mock
    private Child testChild;

    @Mock
    private User testUser;

    @Mock
    private Room testRoom;

    @Mock
    private Booking testBooking;

    private static final BookingState TEST_BOOKING_STATE = BookingState.ACTIVE;

    private static final Set<Integer> TEST_SET_OF_INTEGER = new HashSet<>();

    private static final Long TEST_SIMPLE_LONG = 1L;

    private static final String TEST_SIMPLE_STRING = "Test string";

    private static final Date TEST_DATE_START_TIME;

    private static final Date TEST_DATE_END_TIME;

    private static final String TEST_STRING_DATE_START_TIME = "2019-01-25T12:00:00";

    private static final String TEST_STRING_DATE_END_TIME = "2019-01-25T13:00:00";

    private static final String TEST_STRING_DATE = "2019-01-25";

    private static final String TEST_STRING_TIME = "12:00";


    static {
        Calendar workCalendar = Calendar.getInstance();

        workCalendar.clear();

        workCalendar.set(2019, 0, 25, 12, 0, 0); //2019-01-25T12:00:00 Friday
        TEST_DATE_START_TIME = workCalendar.getTime();

        workCalendar.set(2019, 0, 25, 13, 0, 0); //2019-01-25T13:00:00 Friday
        TEST_DATE_END_TIME = workCalendar.getTime();
    }

    private BookingDto bookingDto;

    @Before
    public void init() {
        bookingDto = new BookingDto();
    }


    @Test
    public void testSetAndGetId () {
        bookingDto.setId(1L);
        assertEquals("get must be equal to set", TEST_SIMPLE_LONG, bookingDto.getId());
    }

    @Test
    public void testSetAndGetDate () {
        bookingDto.setDate(TEST_SIMPLE_STRING);
        assertEquals("get must be equal to set", TEST_SIMPLE_STRING, bookingDto.getDate());
    }

    @Test
    public void testSetAndGetEndDate () {
        bookingDto.setEndDate(TEST_SIMPLE_STRING);
        assertEquals("get must be equal to set", TEST_SIMPLE_STRING, bookingDto.getEndDate());
    }

    @Test
    public void testSetAndGetStartTime() {
        bookingDto.setStartTime(TEST_SIMPLE_STRING);
        assertEquals("get must be equal to set", TEST_SIMPLE_STRING, bookingDto.getStartTime());
    }

    @Test
    public void testSetAndGetEndTime() {
        bookingDto.setEndTime(TEST_SIMPLE_STRING);
        assertEquals("get must be equal to set", TEST_SIMPLE_STRING, bookingDto.getEndTime());
    }

    @Test
    public void testSetAndGetStartTimeMillis() {
        bookingDto.setStartTimeMillis(TEST_SIMPLE_LONG);
        assertEquals("get must be equal to set", TEST_SIMPLE_LONG, bookingDto.getStartTimeMillis());
    }

    @Test
    public void testSetAndGetEndTimeMillis() {
        bookingDto.setEndTimeMillis(TEST_SIMPLE_LONG);
        assertEquals("get must be equal to set", TEST_SIMPLE_LONG, bookingDto.getEndTimeMillis());
    }

    @Test
    public void testSetAndGetDurationBooking() {
        bookingDto.setDurationBooking(TEST_SIMPLE_LONG);
        assertEquals("get must be equal to set", TEST_SIMPLE_LONG, bookingDto.getDurationBooking());
    }

    @Test
    public void testSetAndGetKidName() {
        bookingDto.setKidName(TEST_SIMPLE_STRING);
        assertEquals("get must be equal to set", TEST_SIMPLE_STRING, bookingDto.getKidName());
    }

    @Test
    public void testSetAndGetRoomName() {
        bookingDto.setRoomName(TEST_SIMPLE_STRING);
        assertEquals("get must be equal to set", TEST_SIMPLE_STRING, bookingDto.getRoomName());
    }

    @Test
    public void testSetAndGetDuration() {
        bookingDto.setDuration(TEST_SIMPLE_STRING);
        assertEquals("get must be equal to set", TEST_SIMPLE_STRING, bookingDto.getDuration());
    }

    @Test
    public void testSetAndGetIdChild() {
        bookingDto.setIdChild(TEST_SIMPLE_LONG);
        assertEquals("get must be equal to set", TEST_SIMPLE_LONG, bookingDto.getIdChild());
    }

    @Test
    public void testSetAndGetSum() {
        bookingDto.setSum(TEST_SIMPLE_LONG);
        assertEquals("get must be equal to set", TEST_SIMPLE_LONG, bookingDto.getSum());
    }

    @Test
    public void testSetAndGetDurationLong() {
        bookingDto.setDurationLong(TEST_SIMPLE_LONG);
        assertEquals("get must be equal to set", TEST_SIMPLE_LONG, bookingDto.getDurationLong());
    }

    @Test
    public void testSetAndGetBookingState() {
        bookingDto.setBookingState(TEST_BOOKING_STATE);
        assertEquals("get must be equal to set", TEST_BOOKING_STATE, bookingDto.getBookingState());
    }

    @Test
    public void testSetAndGetComment() {
        bookingDto.setComment(TEST_SIMPLE_STRING);
        assertEquals("get must be equal to set", TEST_SIMPLE_STRING, bookingDto.getComment());
    }

    @Test
    public void testSetAndGetRecurrentId() {
        bookingDto.setRecurrentId(TEST_SIMPLE_LONG);
        assertEquals("get must be equal to set", TEST_SIMPLE_LONG, bookingDto.getRecurrentId());
    }

    @Test
    public void testSetAndGetUserId() {
        bookingDto.setUserId(TEST_SIMPLE_LONG);
        assertEquals("get must be equal to set", TEST_SIMPLE_LONG, bookingDto.getUserId());
    }

    @Test
    public void testSetAndGetKidId() {
        bookingDto.setKidId(TEST_SIMPLE_LONG);
        assertEquals("get must be equal to set", TEST_SIMPLE_LONG, bookingDto.getKidId());
    }

    @Test
    public void testSetAndGetRoomId() {
        bookingDto.setRoomId(TEST_SIMPLE_LONG);
        assertEquals("get must be equal to set", TEST_SIMPLE_LONG, bookingDto.getRoomId());
    }

    @Test
    public void testSetAndGetDaysOfWeek() {
        bookingDto.setDaysOfWeek(TEST_SIMPLE_STRING);
        assertEquals("get must be equal to set", TEST_SIMPLE_STRING, bookingDto.getDaysOfWeek());
    }

    @Test
    public void testSetAndGetWeekDays() {
        bookingDto.setWeekDays(TEST_SET_OF_INTEGER);
        assertEquals("get must be equal to set", TEST_SET_OF_INTEGER, bookingDto.getWeekDays());
    }

    @Test
    public void testSetAndGetChild() {
        bookingDto.setChild(testChild);
        assertEquals("get must be equal to set", testChild, bookingDto.getChild());
    }

    @Test
    public void testSetAndGetUser() {
        bookingDto.setUser(testUser);
        assertEquals("get must be equal to set", testUser, bookingDto.getUser());
    }

    @Test
    public void testSetAndGetRoom() {
        bookingDto.setRoom(testRoom);
        assertEquals("get must be equal to set", testRoom, bookingDto.getRoom());
    }

    @Test
    public void testSetAndGetDateStartTimeWithNullStartTime() {
        bookingDto.setDateStartTime(TEST_DATE_START_TIME);
        assertEquals("get must be equal to set",
                TEST_DATE_START_TIME, bookingDto.getDateStartTime());
        assertEquals("get start time must be appropriate", TEST_STRING_DATE_START_TIME,
                bookingDto.getStartTime());
    }

    @Test
    public void testSetAndGetDateEndTimeWithNullEndTime() {
        bookingDto.setDateEndTime(TEST_DATE_END_TIME);
        assertEquals("get must be equal to set",
                TEST_DATE_END_TIME, bookingDto.getDateEndTime());
        assertEquals("get end time must be appropriate", TEST_STRING_DATE_END_TIME,
                bookingDto.getEndTime());
    }

    @Test
    public void testSetAndGetStartTimeWithSetStartTime() {
        bookingDto.setStartTime(TEST_STRING_DATE_END_TIME);
        bookingDto.setDateStartTime(TEST_DATE_START_TIME);
        assertEquals("get must be equal to set",
                TEST_DATE_START_TIME, bookingDto.getDateStartTime());
        assertEquals("get start time must be appropriate", TEST_STRING_DATE_END_TIME,
                bookingDto.getStartTime());
    }

    @Test
    public void testSetAndGetEndTimeWithSetEndTime() {
        bookingDto.setEndTime(TEST_STRING_DATE_START_TIME);
        bookingDto.setDateEndTime(TEST_DATE_END_TIME);
        assertEquals("get must be equal to set",
                TEST_DATE_END_TIME, bookingDto.getDateEndTime());
        assertEquals("get start time must be appropriate", TEST_STRING_DATE_START_TIME,
                bookingDto.getEndTime());
    }

    @Test
    public void testSetStartTimeWithDateParameter() {
        bookingDto.setStartTime(TEST_DATE_START_TIME);
        assertEquals("get must be equal to set", TEST_STRING_DATE,
                bookingDto.getStartTime());
    }

    @Test
    public void testSetEndTimeWithDateParameter() {
        bookingDto.setEndTime(TEST_DATE_END_TIME);
        assertEquals("get must be equal to set", TEST_STRING_DATE,
                bookingDto.getEndTime());
    }

    @Test
    public void testSetRightEndTime() {
        bookingDto.setRightEndTime(TEST_DATE_START_TIME);
        assertEquals("get must be equal to set", TEST_STRING_TIME,
                bookingDto.getEndTime());
    }

    @Test
    public void testSetRightEndDate() {
        bookingDto.setRightEndDate(TEST_DATE_END_TIME);
        assertEquals("get must be equal to set", TEST_STRING_DATE,
                bookingDto.getEndDate());
    }

    @Test
    public void testSetAllAbsentIdFromBookingWithBookingNull() {
        NullPointerException failException = null;

        try {
            bookingDto.setAllAbsentIdFromBooking(null);
        } catch (NullPointerException e) {
            failException = e;
        }
        assertEquals("the exception must be null", null, failException);
    }

    @Test
    public void testSetAllAbsentIdFromBookingWithAllNull() {
        when(testBooking.getIdBook()).thenReturn(null);
        bookingDto.setAllAbsentIdFromBooking(testBooking);

        assertEquals("the result must be null", null,
                bookingDto.getIdChild());
        assertEquals("the result must be null", null,
                bookingDto.getRoomId());
        assertEquals("the result must be null", null,
                bookingDto.getUserId());
        assertEquals("the result must be null", null,
                bookingDto.getId());

    }

    @Test
    public void testSetAllAbsentIdFromBookingWithoutNull() {
        when(testBooking.getChild()).thenReturn(testChild);
        when(testBooking.getUser()).thenReturn(testUser);
        when(testBooking.getRoom()).thenReturn(testRoom);
        when(testBooking.getIdBook()).thenReturn(TEST_SIMPLE_LONG);
        when(testChild.getId()).thenReturn(TEST_SIMPLE_LONG);
        when(testUser.getId()).thenReturn(TEST_SIMPLE_LONG);
        when(testRoom.getId()).thenReturn(TEST_SIMPLE_LONG);

        bookingDto.setAllAbsentIdFromBooking(testBooking);

        assertEquals("the result must be null", TEST_SIMPLE_LONG,
                bookingDto.getIdChild());
        assertEquals("the result must be null", TEST_SIMPLE_LONG,
                bookingDto.getRoomId());
        assertEquals("the result must be null", TEST_SIMPLE_LONG,
                bookingDto.getUserId());
        assertEquals("the result must be null", TEST_SIMPLE_LONG,
                bookingDto.getId());
    }

    @Test
    public void testSetFieldFromBookingIfNotExistsWithBookingNull() {
        NullPointerException failException = null;

        try {
            bookingDto.setFieldFromBookingIfNotExists(null);
        } catch (NullPointerException e) {
            failException = e;
        }
        assertEquals("the exception must be null", null, failException);
    }

    @Test
    public void testSetFieldFromBookingIfNotExistsWithAllNull() {
        when(testBooking.getChild()).thenReturn(testChild);
        when(testBooking.getUser()).thenReturn(testUser);
        when(testBooking.getRoom()).thenReturn(testRoom);
        when(testBooking.getIdBook()).thenReturn(TEST_SIMPLE_LONG);
        when(testBooking.getBookingStartTime()).thenReturn(TEST_DATE_START_TIME);
        when(testBooking.getBookingEndTime()).thenReturn(TEST_DATE_END_TIME);
        when(testBooking.getComment()).thenReturn(TEST_SIMPLE_STRING);
        when(testBooking.getBookingState()).thenReturn(TEST_BOOKING_STATE);
        when(testBooking.getDuration()).thenReturn(TEST_SIMPLE_LONG);
        when(testBooking.getSum()).thenReturn(TEST_SIMPLE_LONG);
        when(testBooking.getRecurrentId()).thenReturn(TEST_SIMPLE_LONG);

        bookingDto.setFieldFromBookingIfNotExists(testBooking);

        assertEquals("the object must be equals", testChild,
                bookingDto.getChild());
        assertEquals("the object must be equals", testRoom,
                bookingDto.getRoom());
        assertEquals("the object must be equals", testUser,
                bookingDto.getUser());
        assertEquals("the object must be equals", TEST_SIMPLE_LONG,
                bookingDto.getId());
        assertEquals("the object must be equals", TEST_DATE_START_TIME,
                bookingDto.getDateStartTime());
        assertEquals("the object must be equals", TEST_DATE_END_TIME,
                bookingDto.getDateEndTime());
        assertEquals("the object must be equals", TEST_SIMPLE_STRING,
                bookingDto.getComment());
        assertEquals("the object must be equals", TEST_BOOKING_STATE,
                bookingDto.getBookingState());
        assertEquals("the object must be equals", TEST_SIMPLE_LONG,
                bookingDto.getDurationLong());
        assertEquals("the object must be equals", TEST_SIMPLE_LONG,
                bookingDto.getSum());
        assertEquals("the object must be equals", TEST_SIMPLE_LONG,
                bookingDto.getRecurrentId());
    }

    @Test
    public void testSetFieldFromBookingIfNotExistsWithoutNull() {
        setBookingDtoBaseFields();
        bookingDto.setFieldFromBookingIfNotExists(testBooking);

        assertEquals("the object must be equals", testChild,
                bookingDto.getChild());
        assertEquals("the object must be equals", testRoom,
                bookingDto.getRoom());
        assertEquals("the object must be equals", testUser,
                bookingDto.getUser());
        assertEquals("the object must be equals", TEST_SIMPLE_LONG,
                bookingDto.getId());
        assertEquals("the object must be equals", TEST_DATE_START_TIME,
                bookingDto.getDateStartTime());
        assertEquals("the object must be equals", TEST_DATE_END_TIME,
                bookingDto.getDateEndTime());
        assertEquals("the object must be equals", TEST_SIMPLE_STRING,
                bookingDto.getComment());
        assertEquals("the object must be equals", TEST_BOOKING_STATE,
                bookingDto.getBookingState());
        assertEquals("the object must be equals", TEST_SIMPLE_LONG,
                bookingDto.getDurationLong());
        assertEquals("the object must be equals", TEST_SIMPLE_LONG,
                bookingDto.getSum());
        assertEquals("the object must be equals", TEST_SIMPLE_LONG,
                bookingDto.getRecurrentId());
    }

    @Test
    public void testGetListOfBookingObjectsWithInputNull() {
        List<Booking> result = BookingDto.getListOfBookingObjects(null);

        assertTrue("the list must be empty", result.isEmpty());
    }

    @Test
    public void testGetListOfBookingObjectsWithoutInputNull() {
        List<Booking> result = BookingDto
                .getListOfBookingObjects(Arrays.asList(bookingDto, new BookingDto()));

        assertTrue("the size of list is 2", result.size() == 2);
        assertTrue("the class must be appropriate", result.get(0).getClass() == Booking.class);
        assertTrue("the class must be appropriate", result.get(1).getClass() == Booking.class);
    }

    @Test
    public void testSetIdToListOfBookingDtoWithNullDto() {
        List<BookingDto> result =
                BookingDto.setIdToListOfBookingDto(null, Collections.singletonList(testBooking));

        assertTrue("the list must be empty", result.isEmpty());
    }

    @Test
    public void testSetIdToListOfBookingDtoWithNullBookings() {
        List<BookingDto> result =
                BookingDto.setIdToListOfBookingDto(Collections.singletonList(bookingDto), null);

        assertTrue("the list must be empty", result.isEmpty());
    }

    @Test
    public void testSetIdToListOfBookingDtoWithoutHasElements() {
        List<BookingDto> testListOfBookingDto = Collections.emptyList();
        List<Booking> testListOfBooking = Collections.emptyList();
        List<BookingDto> result =
                BookingDto.setIdToListOfBookingDto(testListOfBookingDto, testListOfBooking);

        assertEquals("the objects must be equals", testListOfBookingDto, result);
    }

    @Test
    public void testSetIdToListOfBookingDtoWithElements() {
        Booking secondTestBooking = mock(Booking.class);
        BookingDto secondBookingDto = new BookingDto();
        List<BookingDto> testListOfBookingDto = Arrays.asList(bookingDto, secondBookingDto);
        List<Booking> testListOfBooking = Arrays.asList(testBooking, secondTestBooking);
        when(testBooking.getIdBook()).thenReturn(TEST_SIMPLE_LONG);
        when(secondTestBooking.getIdBook()).thenReturn(2L);
        List<BookingDto> result =
                BookingDto.setIdToListOfBookingDto(testListOfBookingDto, testListOfBooking);

        assertTrue("the size of list is 2", result.size() == 2);
        assertTrue("the objects must be equals", result.get(0) == bookingDto);
        assertTrue("the objects must be equals", result.get(1) == secondBookingDto);
        assertTrue("the objects must be equals", bookingDto.getId() == 1L);
        assertTrue("the objects must be equals", secondBookingDto.getId() == 2L);
    }

    @Test
    public void testGetNewBookingDtoWithNullDates() {
        NullPointerException failException = null;

        try {
            bookingDto.getNewBookingDto(null);
        } catch (NullPointerException e) {
            failException = e;
        }

        assertEquals("the exception must be null", null, failException);
    }

    @Test
    public void testGetNewBookingDtoWithNulls() {
        setBookingDtoBaseFields();
        bookingDto.setStartTime((String)null);
        bookingDto.setEndTime((String)null);
        bookingDto.setChild(null);
        bookingDto.setKidName(null);
        when(testChild.getFullName()).thenReturn(TEST_SIMPLE_STRING);
        BookingDto resultBookingDto =
                bookingDto.getNewBookingDto(new Date[] {TEST_DATE_START_TIME, null});

        assertEquals("the object must be equals", null,
                resultBookingDto.getChild());
        assertEquals("the object must be equals", testRoom,
                resultBookingDto.getRoom());
        assertEquals("the object must be equals", testUser,
                resultBookingDto.getUser());
        assertEquals("the object must be equals", null,
                resultBookingDto.getId());
        assertEquals("the object must be equals", TEST_DATE_START_TIME,
                resultBookingDto.getDateStartTime());
        assertEquals("the object must be equals", null,
                resultBookingDto.getDateEndTime());
        assertEquals("the object must be equals", TEST_STRING_DATE_START_TIME,
                resultBookingDto.getStartTime());
        assertEquals("the object must be equals", null,
                resultBookingDto.getEndTime());
        assertEquals("the object must be equals", TEST_SIMPLE_STRING,
                resultBookingDto.getComment());
        assertEquals("the object must be equals", BookingState.BOOKED,
                resultBookingDto.getBookingState());
        assertEquals("the object must be equals", (Long)0L,
                resultBookingDto.getDurationLong());
        assertEquals("the object must be equals", (Long)0L,
                resultBookingDto.getSum());
        assertEquals("the object must be equals", TEST_SIMPLE_LONG,
                resultBookingDto.getRecurrentId());
        assertEquals("the object must be equals", null,
                resultBookingDto.getKidName());

    }

    @Test
    public void testGetNewBookingDtoWithoutNulls() {
        setBookingDtoBaseFields();
        when(testChild.getFullName()).thenReturn(TEST_SIMPLE_STRING);
        BookingDto resultBookingDto =
                bookingDto.getNewBookingDto(
                        new Date[] {TEST_DATE_START_TIME, TEST_DATE_END_TIME});

        assertEquals("the object must be equals", testChild,
                resultBookingDto.getChild());
        assertEquals("the object must be equals", testRoom,
                resultBookingDto.getRoom());
        assertEquals("the object must be equals", testUser,
                resultBookingDto.getUser());
        assertEquals("the object must be equals", null,
                resultBookingDto.getId());
        assertEquals("the object must be equals", TEST_DATE_START_TIME,
                resultBookingDto.getDateStartTime());
        assertEquals("the object must be equals", TEST_DATE_END_TIME,
                resultBookingDto.getDateEndTime());
        assertEquals("the object must be equals", TEST_STRING_DATE_START_TIME,
                resultBookingDto.getStartTime());
        assertEquals("the object must be equals", TEST_STRING_DATE_END_TIME,
                resultBookingDto.getEndTime());
        assertEquals("the object must be equals", TEST_SIMPLE_STRING,
                resultBookingDto.getComment());
        assertEquals("the object must be equals", BookingState.BOOKED,
                resultBookingDto.getBookingState());
        assertEquals("the object must be equals", (Long)3600_000L,
                resultBookingDto.getDurationLong());
        assertEquals("the object must be equals", (Long)0L,
                resultBookingDto.getSum());
        assertEquals("the object must be equals", TEST_SIMPLE_LONG,
                resultBookingDto.getRecurrentId());
        assertEquals("the object must be equals", TEST_SIMPLE_STRING,
                resultBookingDto.getKidName());
    }

    @Test
    public void testGetBookingObjectWithNull() {

    }

    @Test
    public void testGetBookingObjectWithoutNull() {

    }

    @Test
    public void testGetRecurrentBookingDtoWithBookingsNull() {

    }

    @Test
    public void testGetRecurrentBookingDtoWithEmptyBookings() {

    }

    @Test
    public void testGetRecurrentBookingDtoWithFullBookings() {

    }

    private void setBookingDtoBaseFields() {
        bookingDto.setChild(testChild);
        bookingDto.setUser(testUser);
        bookingDto.setRoom(testRoom);
        bookingDto.setId(TEST_SIMPLE_LONG);
        bookingDto.setDateStartTime(TEST_DATE_START_TIME);
        bookingDto.setDateEndTime(TEST_DATE_END_TIME);
        bookingDto.setComment(TEST_SIMPLE_STRING);
        bookingDto.setBookingState(TEST_BOOKING_STATE);
        bookingDto.setDurationLong(TEST_SIMPLE_LONG);
        bookingDto.setSum(TEST_SIMPLE_LONG);
        bookingDto.setRecurrentId(TEST_SIMPLE_LONG);
        bookingDto.setStartTime(TEST_STRING_DATE_START_TIME);
        bookingDto.setEndTime(TEST_STRING_DATE_END_TIME);
        bookingDto.setIdChild(TEST_SIMPLE_LONG);
        bookingDto.setKidId(TEST_SIMPLE_LONG);
        bookingDto.setKidName(TEST_SIMPLE_STRING);
    }

}
