package ua.softserveinc.tc.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.util.DateUtil;

import java.util.Collections;
import java.util.List;


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
public class RecurrentBookingValidatorTest {

    @Mock
    private Logger logger;

    @Mock
    private BookingDto bookingDto;

    @Mock
    private BookingService bookingService;

    @Mock
    private BookingValidator bookingValidator;

    @InjectMocks
    private RecurrentBookingValidatorImpl recurrentBookingValidator;

    @Before
    public void initValidBookingDto() {
        when(bookingDto.getDaysOfWeek()).thenReturn("Mon Tue Fri");
    }

    @Test
    public void testIsValidToInsertHasNullListOfBookingDto() {
        testNullOrEmptyFailInsert("list of dto is null", null);

    }

    @Test
    public void testIsValidToInsertHasEmptyListOfBookingDto() {
        testNullOrEmptyFailInsert("list of dto is empty", Collections.emptyList());

    }

    @Test
    public void testIsValidToInsertHasNullWeekOfDays() {
        when(bookingDto.getDaysOfWeek()).thenReturn(null);

        testNullOrEmptyFailInsert("has null week of Days", Collections.singletonList(bookingDto));

    }

    @Test
    public void testIsValidToInsertHasNotCorrectWeekOfDays() {
        mockStatic(DateUtil.class);
        when(DateUtil.getDayOfWeek(any())).thenReturn(null);

        testNullOrEmptyFailInsert("has not correct week of Days", Collections.singletonList(bookingDto));

    }

    @Test
    public void testIsValidToInsertIsNotValidToInsertForBookingValidator() {
        String errorMessage = "Error Message";
        mockStatic(DateUtil.class);

        when(DateUtil.getDayOfWeek(any())).thenReturn(1);
        when(bookingValidator.getErrors()).thenReturn(Collections.singletonList("Error Message"));
        when(bookingValidator.isValidToInsert(Collections.singletonList(bookingDto)))
                .thenReturn(false);

        commonTestFailInsert("not valid for plain insert", Collections.singletonList(bookingDto),
                errorMessage);

    }

    @Test
    public void testIsValidToInsertFullValid() {
        setValidToInsert();

        assertTrue("failure(is full valid) - result must be true", recurrentBookingValidator
                .isValidToInsert(Collections.singletonList(bookingDto)));

        assertEquals("failure(the object is valid) - size should be 0", 0,
                recurrentBookingValidator.getErrors().size());

    }

    @Test
    public void testisValidToUpdateNotValidToInsert() {
        when(bookingDto.getDaysOfWeek()).thenReturn(null);

        assertFalse("failure(not valid to insert) - result must be false", recurrentBookingValidator
                .isValidToUpdate(Collections.singletonList(bookingDto)));

    }

    @Test
    public void testIsValidToUpdateNullBooking() {
        setValidToInsert();

        when(bookingService.findEntityById(any())).thenReturn(null);

        testNullOrNotCorrectFailUpdate("failure(not valid to insert) - result must be false",
                Collections.singletonList(bookingDto));

    }

    @Test
    public void testIsValidToUpdateThrowException() {
        ResourceNotFoundException thrownException = mock(ResourceNotFoundException.class);

        setValidToInsert();

        when(bookingService.findEntityById(any())).thenThrow(thrownException);

        testNullOrNotCorrectFailUpdate("failure(not valid to insert) - result must be false",
                Collections.singletonList(bookingDto));

        verify(logger, times(1)).error(any());

    }

    @Test
    public void testIsValidToUpdateDifferentRecurrentId() {
        Booking booking = mock(Booking.class);
        Long recurrentIdFromBooking = 1L;
        Long recurrentIdFromBookingDto = 2L;

        setValidToInsert();

        when(bookingService.findEntityById(any())).thenReturn(booking);
        when(booking.getRecurrentId()).thenReturn(recurrentIdFromBooking);
        when(bookingDto.getRecurrentId()).thenReturn(recurrentIdFromBookingDto);


        testNullOrNotCorrectFailUpdate("failure(not equals recurrent id) - result must be false",
                Collections.singletonList(bookingDto));

    }

    @Test
    public void testIsValidToUpdateStateNotBooked() {
        Booking booking = mock(Booking.class);
        Long commonRecurrentId = 1L;

        setValidToInsert();

        when(bookingService.findEntityById(any())).thenReturn(booking);
        when(booking.getRecurrentId()).thenReturn(commonRecurrentId);
        when(bookingDto.getRecurrentId()).thenReturn(commonRecurrentId);
        when(booking.getBookingState()).thenReturn(BookingState.CANCELLED);


        testNullOrNotCorrectFailUpdate("failure(not equals recurrent id) - result must be false",
                Collections.singletonList(bookingDto));

    }

    @Test
    public void testIsValidToUpdateFullValid() {
        Booking booking = mock(Booking.class);
        Long commonRecurrentId = 1L;

        setValidToInsert();

        when(bookingService.findEntityById(any())).thenReturn(booking);
        when(booking.getRecurrentId()).thenReturn(commonRecurrentId);
        when(bookingDto.getRecurrentId()).thenReturn(commonRecurrentId);
        when(booking.getBookingState()).thenReturn(BookingState.BOOKED);


        assertTrue("failure(is full valid) - result must be true", recurrentBookingValidator
                .isValidToInsert(Collections.singletonList(bookingDto)));

        assertEquals("failure(the object is valid) - size should be 0", 0,
                recurrentBookingValidator.getErrors().size());

    }

    private void testNullOrEmptyFailInsert(String outputMessagePart, List<BookingDto> bookingDtos) {
        commonTestFailInsert(outputMessagePart, bookingDtos,
                ValidationConstants.COMMON_ERROR_MESSAGE);
    }

    private void testNullOrNotCorrectFailUpdate(String outputMessagePart,
                                                List<BookingDto> bookingDtos) {

        assertFalse("failure(" + outputMessagePart + ") - result must be false",
                recurrentBookingValidator.isValidToUpdate(bookingDtos));

        assertEquals("failure(" + outputMessagePart + ") - size should be 1", 1,
                recurrentBookingValidator.getErrors().size());

        assertEquals("failure(" + outputMessagePart + ")", ValidationConstants.COMMON_ERROR_MESSAGE,
                recurrentBookingValidator.getErrors().get(0));

    }

    private void commonTestFailInsert(String outputMessagePart, List<BookingDto> bookingDtos,
                                      String messageForTest) {

        assertFalse("failure(" + outputMessagePart + ") - result must be false",
                recurrentBookingValidator.isValidToInsert(bookingDtos));

        assertEquals("failure(" + outputMessagePart + ") - size should be 1", 1,
                recurrentBookingValidator.getErrors().size());

        assertEquals("failure(" + outputMessagePart + ")", messageForTest,
                recurrentBookingValidator.getErrors().get(0));
    }

    private void setValidToInsert() {
        mockStatic(DateUtil.class);
        when(DateUtil.getDayOfWeek(any())).thenReturn(1);
        when(bookingValidator.isValidToInsert(Collections.singletonList(bookingDto)))
                .thenReturn(true);
    }

}
