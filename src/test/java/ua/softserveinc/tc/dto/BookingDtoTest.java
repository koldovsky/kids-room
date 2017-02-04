package ua.softserveinc.tc.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DateUtil.class, BookingDto.class})
public class BookingDtoTest {

    private static final Child testChild = mock(Child.class);

    private static final User testUser = mock(User.class);

    private static final Room testRoom = mock(Room.class);

    private static final BookingState testBookingState = BookingState.ACTIVE;

    private static final Set<Integer> testSetOfInteger = new HashSet<>();

    private static final Long TEST_SIMPLE_LONG = 1L;

    private static final String TEST_SIMPLE_STRING = "Test string";

    private static final Date TEST_DATE_START_TIME;

    private static final Date TEST_DATE_END_TIME;

    private static final String TEST_STRING_DATE_START_TIME = "2019-01-25T12:00:00";

    private static final String TEST_STRING_DATE_END_TIME = "2019-01-25T13:00:00";

    static {
        Calendar workCalendar = Calendar.getInstance();

        workCalendar.clear();

        workCalendar.set(2019, 0, 25, 12, 0, 0); //2019-01-25T12:00:00 Friday
        TEST_DATE_START_TIME = workCalendar.getTime();

        workCalendar.set(2019, 0, 25, 13, 0, 0); //2019-01-25T13:00:00 Friday
        TEST_DATE_END_TIME = workCalendar.getTime();
    }

    private final BookingDto bookingDto = new BookingDto();

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
        bookingDto.setBookingState(testBookingState);
        assertEquals("get must be equal to set", testBookingState, bookingDto.getBookingState());
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
        bookingDto.setWeekDays(testSetOfInteger);
        assertEquals("get must be equal to set", testSetOfInteger, bookingDto.getWeekDays());
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

}
