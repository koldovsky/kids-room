package ua.softserveinc.tc.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;

import java.util.Collections;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputDateTimeValidatorTest {

    @Mock
    private RoomService roomService;

    @Mock
    private BookingDto bookingDto;

    @Mock
    private Room room;

    @InjectMocks
    private InputDateTimeValidatorImpl inputDateValidator;

    @Before
    public void initValidBookingDto() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:00:00");
        when(bookingDto.getEndTime()).thenReturn("2018-01-27T16:00:00");
    }

    @Test
    public void testValidHasNullListOfBookingDto() {
        testNullOrEmptyFail("list of dro is null", null);

    }

    @Test
    public void testValidHasEmptyListOfBookingDto() {
        testNullOrEmptyFail("list of dto is empty", Collections.emptyList());

    }

    @Test
    public void testValidHasNullStartTime() {
        when(bookingDto.getStartTime()).thenReturn(null);

        testNullOrEmptyFail("the start time is null", Collections.singletonList(bookingDto));

    }

    @Test
    public void testValidHasNullEndTime() {
        when(bookingDto.getEndTime()).thenReturn(null);

        testNullOrEmptyFail("the end time is null", Collections.singletonList(bookingDto));

    }

    @Test
    public void testValidHasLetterInYearsPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("20X8-01-27T15:00:00");

        testTimeFormatFail("letter in years part");
    }

    @Test
    public void testValidHasLetterInMonthsPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-X1-27T15:00:00");

        testTimeFormatFail("letter in months part");
    }

    @Test
    public void testValidHasLetterInDaysPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-X7T15:00:00");

        testTimeFormatFail("letter in days part");
    }

    @Test
    public void testValidHasLetterInHoursPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T1X:00:00");

        testTimeFormatFail("letter in hours part");
    }

    @Test
    public void testValidHasLetterInMinutesPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:X0:00");

        testTimeFormatFail("letter in minutes part");
    }

    @Test
    public void testValidHasLetterInSecondsPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:00:0X");

        testTimeFormatFail("letter in seconds part");
    }

    @Test
    public void testValidHasRedundantNumberInYearsPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("20180-01-27T15:00:00");

        testTimeFormatFail("redundant number in years part");
    }

    @Test
    public void testValidHasRedundantNumberInMonthPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-001-27T15:00:00");

        testTimeFormatFail("redundant number in months part");
    }

    @Test
    public void testValidHasRedundantNumberInDayPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-027T15:00:00");

        testTimeFormatFail("redundant number in days part");
    }

    @Test
    public void testValidHasRedundantNumberInHoursPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T015:00:00");

        testTimeFormatFail("redundant number in hours part");
    }

    @Test
    public void testValidHasRedundantNumberInMinutesPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:000:00");

        testTimeFormatFail("redundant number in minutes part");
    }

    @Test
    public void testValidHasRedundantNumberInSecondsPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:00:000");

        testTimeFormatFail("redundant number in seconds part");
    }

    @Test
    public void testValidHasAbsentNumberInYearsPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("018-01-27T15:00:00");

        testTimeFormatFail("absent number in years part");
    }

    @Test
    public void testValidHasAbsentNumberInMonthsPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-1-27T15:00:00");

        testTimeFormatFail("absent number in month part");
    }

    @Test
    public void testValidHasAbsentNumberInDaysPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-7T15:00:00");

        testTimeFormatFail("absent number in days part");
    }

    @Test
    public void testValidHasAbsentNumberInHoursPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T5:00:00");

        testTimeFormatFail("absent number in hours part");
    }

    @Test
    public void testValidHasAbsentNumberInMinutesPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:0:00");

        testTimeFormatFail("absent number in minutes part");
    }

    @Test
    public void testValidHasAbsentNumberInSecondsPartOfStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:00:0");

        testTimeFormatFail("absent number in seconds part");
    }

    @Test
    public void testValidHasSlashInsteadOfHyphenInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01/27T15:00:00");

        testTimeFormatFail("has slash instead of hyphen");
    }

    @Test
    public void testValidHasBackSlashInsteadOfHyphenInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01\27T15:00:00");

        testTimeFormatFail("has back slash instead of hyphen");
    }

    @Test
    public void testValidHasDoubleBackSlashInsteadOfHyphenInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01\\27T15:00:00");

        testTimeFormatFail("has double back slash instead of hyphen");
    }

    @Test
    public void testValidHasDotInsteadOfHyphenInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01.27T15:00:00");

        testTimeFormatFail("has dot instead of hyphen");
    }

    @Test
    public void testValidHasDotInsteadOfColonInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:00.00");

        testTimeFormatFail("has dot instead of colon");
    }

    @Test
    public void testValidDoesNotHaveTInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27 15:00:00");

        testTimeFormatFail("doesn't have 'T' in middle");
    }

    @Test
    public void testValidNegativeNumberInDatePartInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("-2018-01-27T15:00:00");

        testTimeFormatFail("negative number in date part");
    }

    @Test
    public void testValidNegativeNumberInTimePartInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T-15:00:00");

        testTimeFormatFail("negative number in time part");
    }

    @Test
    public void testValidTwentyFourHoursInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T24:00:00");

        testTimeFormatFail("24 hours");
    }

    @Test
    public void testValidSixtyMinutesInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:60:00");

        testTimeFormatFail("60 minutes");
    }

    @Test
    public void testValidSixtySecondsInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:00:60");

        testTimeFormatFail("60 seconds");
    }

    @Test
    public void testValidThirteenMonthsInStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2018-13-27T15:00:00");

        testTimeFormatFail("13 months");
    }

    @Test
    public void testValidThirtyOneDaysInAprilInStartTime() {  //There are 30 days in April
        when(bookingDto.getStartTime()).thenReturn("2018-04-31T15:00:00");

        testTimeFormatFail("31 days in April");
    }

    @Test
    public void testValidTwentyNineDaysInFebruaryInNotLeapYearInStartTime() {  //not leap y -> 28 d
        when(bookingDto.getStartTime()).thenReturn("2017-02-29T15:00:00");

        testTimeFormatFail("29 February not leap year");
    }

    @Test
    public void testValidHasNotValidEndDate() {  //February in non leap year cannot contain 29th day
        when(bookingDto.getEndTime()).thenReturn("2019-02-29T12:15:45");

        testTimeFormatFail("29 February not leap year");
    }

    @Test
    public void testValidHasEndTimeNotAfterStartTime() {
        when(bookingDto.getStartTime()).thenReturn("2100-01-01T12:00:00");
        when(bookingDto.getEndTime()).thenReturn("2100-01-01T12:00:00");

        testTimeRelationsFail("end time is not after start time");

    }

    @Test
    public void testValidHasStartTimeInThePast() {
        when(bookingDto.getStartTime()).thenReturn("2017-01-28T19:57:00");

        testTimeRelationsFail("start time in the past");
    }

    @Test
    public void testValidEndTimeOutOfRangeWithoutSetRoom() {
        when(roomService.findEntityById(any())).thenReturn(room);
        when(bookingDto.getEndTime()).thenReturn("2018-01-27T20:00:01");
        when(room.getWorkingHoursStart()).thenReturn("07:00:00");
        when(room.getWorkingHoursEnd()).thenReturn("20:00:00");

        testTimeOutOfWorkingHoursFail("end time is out of working hours");
    }

    @Test
    public void testValidEndTimeOutOfRangeWithSetRoom() {
        when(bookingDto.getRoom()).thenReturn(room);
        when(bookingDto.getEndTime()).thenReturn("2018-01-27T18:00:01");
        when(room.getWorkingHoursStart()).thenReturn("08:00:00");
        when(room.getWorkingHoursEnd()).thenReturn("18:00:00");

        testTimeOutOfWorkingHoursFail("end time is out of working hours");
    }

    @Test
    public void testValidStartTimeOutOfRangeWithoutSetRoom() {
        when(roomService.findEntityById(any())).thenReturn(room);
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T06:59:59");
        when(room.getWorkingHoursStart()).thenReturn("07:00:00");
        when(room.getWorkingHoursEnd()).thenReturn("20:00:00");

        testTimeOutOfWorkingHoursFail("start time is out of working hours");
    }

    @Test
    public void testValidStartTimeOutOfRangeWithSetRoom() {
        when(bookingDto.getRoom()).thenReturn(room);
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T07:59:59");
        when(room.getWorkingHoursStart()).thenReturn("08:00:00");
        when(room.getWorkingHoursEnd()).thenReturn("18:00:00");

        testTimeOutOfWorkingHoursFail("start time is out of working hours");
    }

    @Test
    public void testValidWhenIsFullValid() {
        when(bookingDto.getRoom()).thenReturn(room);
        when(room.getWorkingHoursStart()).thenReturn("15:00:00");
        when(room.getWorkingHoursEnd()).thenReturn("16:00:00");

        assertTrue("failure(the object is valid) - result must be true", inputDateValidator
                .validate(Collections.singletonList(bookingDto)));

        assertEquals("failure(the object is valid) - size should be 0", 0,
                inputDateValidator.getErrors().size());

    }

    private void testNullOrEmptyFail(String outputMessagePart, List<BookingDto> bookingDtos) {
        commonTestFail(outputMessagePart, bookingDtos, ValidationConstants.COMMON_ERROR_MESSAGE);
    }

    private void testTimeFormatFail(String outputMessagePart) {
        commonTestFail(outputMessagePart, Collections.singletonList(bookingDto),
                ValidationConstants.BAD_TIME_FORMAT);
    }

    private void testTimeRelationsFail(String outputMessagePart) {
        commonTestFail(outputMessagePart, Collections.singletonList(bookingDto),
                ValidationConstants.END_TIME_BEFORE_START_TIME);
    }

    private void testTimeOutOfWorkingHoursFail(String outputMessagePart) {
        commonTestFail(outputMessagePart, Collections.singletonList(bookingDto),
                ValidationConstants.OUT_OF_WORKING_HOURS);
    }

    private void commonTestFail(String outputMessagePart, List<BookingDto> bookingDtos,
                                String messageForTest) {

        assertFalse("failure(" + outputMessagePart + ") - result must be false", inputDateValidator
                .validate(bookingDtos));

        assertEquals("failure(" + outputMessagePart + ") - size should be 1", 1,
                inputDateValidator.getErrors().size());

        assertEquals("failure(" + outputMessagePart + ")", messageForTest,
                inputDateValidator.getErrors().get(0));
    }

}
