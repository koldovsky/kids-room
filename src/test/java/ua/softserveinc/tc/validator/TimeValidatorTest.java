package ua.softserveinc.tc.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.impl.RoomServiceImpl;

import java.text.ParseException;

public class TimeValidatorTest {

    private Room room;


    private BookingDto bookingDto;

    @Mock
    private RoomServiceImpl roomService;

    @InjectMocks
    private TimeValidator timeValidator;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        room = new Room();
        bookingDto = new BookingDto();

        room.setWorkingHoursStart("07:00");
        room.setWorkingHoursEnd("20:00");
        bookingDto.setRoomId(1L);
        bookingDto.setStartTime("08:00");
        Mockito.when(roomService.findById(bookingDto.getRoomId())).thenReturn(room);
    }

    @Test
    public void validateRoomStartTimePassCorectParameters(){
        Assert.assertTrue(timeValidator.validateRoomStartTime(bookingDto));
    }

    @Test
    public void validateRoomStartTimePassStartTimeOutOfRange(){
        final String startTime = "21:30";
        bookingDto.setStartTime(startTime);
        Assert.assertFalse(timeValidator.validateRoomStartTime(bookingDto));
    }

    @Test
    public void validateBookingPassCorrectParameters(){
        bookingDto.setStartTime("07:00");
        bookingDto.setEndTime("16:00");
        Assert.assertTrue(timeValidator.validateBooking(bookingDto));
    }

    @Test
    public void validateBookingPassStartTimeLaterThenEnd(){
        bookingDto.setStartTime("10:00");
        bookingDto.setEndTime("09:00");
        Assert.assertFalse(timeValidator.validateBooking(bookingDto));
    }

    @Test
    public void validateBookingPassStartTimeEqualsEnd(){
        bookingDto.setStartTime("09:00");
        bookingDto.setEndTime("09:00");
        Assert.assertTrue(timeValidator.validateBooking(bookingDto));
    }

    @Test
    public void testCorrectTimeExpectTrue() throws ParseException {
        final String startDate = "2016-11-11";
        final String endDate = "2016-12-31";
        final String message = "end date should be greater than end date";
        Assert.assertTrue(message, timeValidator.validateDate(startDate, endDate));
    }

    @Test
    public void testEqualTimeExpectTrue() throws ParseException {
        final String startDate = "2016-11-11";
        final String endDate = "2016-11-11";
        final String message = "end date should be greater than end date";
        Assert.assertTrue(message, timeValidator.validateDate(startDate, endDate));
    }

    @Test
    public void testFromTimeGreaterThanToTimeExpectFalse() throws ParseException {
        final String startDate = "2016-11-11";
        final String endDate = "2016-10-31";
        final String message = "end date should be greater than end date";
        Assert.assertFalse(message, timeValidator.validateDate(startDate, endDate));
    }

    @Test
    public void testCorrectDateFormatExpectTrue() {
        final String correctDate = "2016-10-10";
        final boolean expected = true;
        final boolean actual = timeValidator.validateDateFormat(correctDate);
        final String message = "the format of date have to be YYYY-MM-DD";
        Assert.assertEquals(message, expected, actual);
    }

    @Test
    public void testInCorrectDateFormatExpectFalse() {
        final String correctDate = "201611-10-102";
        final boolean expected = false;
        final boolean actual = timeValidator.validateDateFormat(correctDate);
        final String message = "the format of date have to be YYYY-MM-DD";
        Assert.assertEquals(message, expected, actual);
    }
}
