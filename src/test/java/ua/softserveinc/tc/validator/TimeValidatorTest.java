package ua.softserveinc.tc.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.impl.RoomServiceImpl;

import java.text.ParseException;

import static org.mockito.Mockito.when;

public class TimeValidatorTest {

    @Mock
    private Room room;

    @Mock
    private BookingDto bookingDto;

    @Mock
    private RoomServiceImpl roomService;

    @InjectMocks
    private TimeValidatorImpl timeValidatorImpl;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        when(room.getWorkingHoursStart()).thenReturn("07:00");
        when(room.getWorkingHoursEnd()).thenReturn("20:00");
        when(bookingDto.getRoomId()).thenReturn(1L);
        when(bookingDto.getStartTime()).thenReturn("08:00");
        when(roomService.findByIdTransactional(bookingDto.getRoomId())).thenReturn(room);
    }

    @Test
    public void validateDateTimeFormatPassTCorrectParam() {
        String dateTime = "2016-11-11T07:00";
        Assert.assertTrue(timeValidatorImpl.validateDateTimeFormat(dateTime));
    }

    @Test
    public void validateDateTimeFormatPassSpaceCorrectParam() {
        String dateTime = "2016-11-11 07:00";
        Assert.assertTrue(timeValidatorImpl.validateDateTimeFormat(dateTime));
    }

    @Test
    public void validateTimeFormatPassCorrectParameters() {
        Assert.assertTrue(timeValidatorImpl.validateTimeFormat("07:00"));
        Assert.assertTrue(timeValidatorImpl.validateTimeFormat("07:00:00"));
    }

    @Test
    public void validateTimeFormatPassIncorrectParameters() {
        Assert.assertFalse(timeValidatorImpl.validateTimeFormat("7:00"));
        Assert.assertFalse(timeValidatorImpl.validateTimeFormat("T07:00"));
        Assert.assertFalse(timeValidatorImpl.validateTimeFormat("25:00"));
        Assert.assertFalse(timeValidatorImpl.validateTimeFormat("-07:00"));
        Assert.assertFalse(timeValidatorImpl.validateTimeFormat("24:60"));
    }

    @Test
    public void validateRoomStartTimePassCorectParameters() {
        Assert.assertTrue(timeValidatorImpl.validateRoomTime(bookingDto));
    }

    @Test
    public void validateRoomStartTimePassStartTimeOutOfRange(){
        final String startTime = "21:30";
        when(bookingDto.getStartTime()).thenReturn(startTime);
        Assert.assertFalse(timeValidatorImpl.validateRoomTime(bookingDto));
    }

    @Test
    public void validateBookingPassCorrectParameters(){
        when(bookingDto.getStartTime()).thenReturn("2016-11-11T07:00");
        when(bookingDto.getEndTime()).thenReturn("2016-11-11T16:00");
        Assert.assertTrue(timeValidatorImpl.validateBooking(bookingDto));
    }

    @Test
    public void validateBookingPassStartTimeLaterThenEnd(){
        when(bookingDto.getStartTime()).thenReturn("2016-11-11T10:00");
        when(bookingDto.getEndTime()).thenReturn("2016-11-11T09:00");
        Assert.assertFalse(timeValidatorImpl.validateBooking(bookingDto));
    }

    @Test
    public void validateBookingPassStartTimeEqualsEnd(){
        when(bookingDto.getStartTime()).thenReturn("2016-11-11T09:00");
        when(bookingDto.getEndTime()).thenReturn("2016-11-11T09:00");
        Assert.assertTrue(timeValidatorImpl.validateBooking(bookingDto));
    }

    @Test
    public void testCorrectTimeExpectTrue() throws ParseException {
        final String startDate = "2016-11-11";
        final String endDate = "2016-12-12";
        final String message = "end date should be greater than end date";
        Assert.assertTrue(message, timeValidatorImpl.isStartDateBefore(startDate, endDate));
    }

    @Test
    public void testEqualTimeExpectTrue() throws ParseException {
        final String startDate = "2016-11-11";
        final String endDate = "2016-11-11";
        final String message = "end date should be greater than end date";
        Assert.assertTrue(message, timeValidatorImpl.isStartDateBefore(startDate, endDate));
    }

    @Test
    public void testFromTimeGreaterThanToTimeExpectFalse() throws ParseException {
        final String startDate = "2016-11-11";
        final String endDate = "2016-10-31";
        final String message = "end date should be greater than end date";
        Assert.assertFalse(message, timeValidatorImpl.isStartDateBefore(startDate, endDate));
    }

    @Test
    public void testCorrectDateFormatExpectTrue() {
        final String correctDate = "2016-10-10";
        final boolean expected = true;
        final boolean actual = timeValidatorImpl.validateDateFormat(correctDate);
        final String message = "the format of date have to be YYYY-MM-DD";
        Assert.assertEquals(message, expected, actual);
    }

    @Test
    public void testInCorrectDateFormatExpectFalse() {
        final String correctDate = "201611-10-102";
        final boolean expected = false;
        final boolean actual = timeValidatorImpl.validateDateFormat(correctDate);
        final String message = "the format of date have to be YYYY-MM-DD";
        Assert.assertEquals(message, expected, actual);
    }
}
