package ua.softserveinc.tc.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.util.BookingsCharacteristics;

import java.util.Collections;
import java.util.Date;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class BookingValidatorTest {

    @Mock
    private BookingDto bookingDto;

    @Mock
    private BookingService bookingService;

    @Mock
    private InputDateValidatorImpl inputDateValidator;

    @Mock
    private Logger logger;

    @InjectMocks
    private BookingValidatorImpl bookingValidator;


    @Test
    public void testIsValidToInsertBookingDtoIsNull() {
        assertFalse("failure(dto is null) - result should be false", bookingValidator
                .isValidToInsert(null));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(dto is null)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void testIsValidToInsertListOfBookingDtoIsEmpty() {
        assertFalse("failure(List is empty) - result should be false", bookingValidator
                .isValidToInsert(Collections.emptyList()));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(List is empty)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void testIsValidToInsertBookingDtoHasNullUserId() {
        when(validBookingDto().getUserId()).thenReturn(null);

        assertFalse("failure(UserId is null) - result should be false", bookingValidator
                .isValidToInsert(Collections.singletonList(bookingDto)));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(UserId is null)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void testIsValidToInsertBookingDtoHasNullRoomId() {
        when(validBookingDto().getRoomId()).thenReturn(null);

        assertFalse("failure(RoomId is null) - result should be false", bookingValidator
                .isValidToInsert(Collections.singletonList(bookingDto)));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(RoomId is null)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void testIsValidToInsertBookingDtoHasNullKidId() {
        when(validBookingDto().getKidId()).thenReturn(null);

        assertFalse("failure(KidId is null) - result should be false", bookingValidator
                .isValidToInsert(Collections.singletonList(bookingDto)));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(KidId is null)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void testIsValidToInsertBookingDtoHasNullComment() {
        when(validBookingDto().getComment()).thenReturn(null);

        assertFalse("failure(Comment is null) - result should be false", bookingValidator
                .isValidToInsert(Collections.singletonList(bookingDto)));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Comment is null)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void testIsValidToInsertBookingDtoHasNullStartTime() {
        when(validBookingDto().getStartTime()).thenReturn(null);

        assertFalse("failure(Start time is null) - result should be false", bookingValidator
                .isValidToInsert(Collections.singletonList(bookingDto)));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Start time is null)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void testIsValidToInsertBookingDtoHasNullEndTime() {
        when(validBookingDto().getEndTime()).thenReturn(null);

        assertFalse("failure(End time is null) - result should be false", bookingValidator
                .isValidToInsert(Collections.singletonList(bookingDto)));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Start time is null)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void testIsValidToInsertBookingDtoHasNotCorrectData() {
        List<BookingDto> listOfNotCorrectBookingDto = Collections.singletonList(bookingDto);
        validBookingDto();

        when(bookingService.normalizeBookingDtoObjects(listOfNotCorrectBookingDto))
                .thenReturn(false);

        assertFalse("failure(Not correct data) - result should be false", bookingValidator
                .isValidToInsert(listOfNotCorrectBookingDto));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Not correct data)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void testIsValidToInsertBookingDtoHasNotValidDate() {
        List<BookingDto> listOfNotCorrectBookingDto = Collections.singletonList(bookingDto);
        String notValidDateErrorMessage = "Error! Not valid date!";
        List<String> notValidDateErrorMessageList =
                Collections.singletonList(notValidDateErrorMessage);
        validBookingDto();

        when(bookingService.normalizeBookingDtoObjects(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(inputDateValidator.validate(listOfNotCorrectBookingDto))
                .thenReturn(false);
        when(inputDateValidator.getErrors())
                .thenReturn(notValidDateErrorMessageList);

        assertFalse("failure(Not valid date) - result should be false", bookingValidator
                .isValidToInsert(listOfNotCorrectBookingDto));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Not valid date)", notValidDateErrorMessage,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void testIsValidToInsertBookingDtoHasDuplicates() {
        List<BookingDto> listOfNotCorrectBookingDto = Collections.singletonList(bookingDto);
        validBookingDto();

        when(bookingService.normalizeBookingDtoObjects(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(inputDateValidator.validate(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(bookingService.hasDuplicateBookings(listOfNotCorrectBookingDto))
                .thenReturn(true);

        assertFalse("failure(Duplicate bookings) - result should be false", bookingValidator
                .isValidToInsert(listOfNotCorrectBookingDto));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Duplicate bookings)", ValidationConstants.DUPLICATE_BOOKING_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void testIsValidToInsertBookingDtoHasNotAvailablePlaces() {
        List<BookingDto> listOfNotCorrectBookingDto = Collections.singletonList(bookingDto);
        Long recurrentId = 1L;
        Long bookingId = 1L;
        validBookingDto();

        Date startDate = mock(Date.class);
        Date endDate = mock(Date.class);
        Room room = mock(Room.class);

        when(bookingService.normalizeBookingDtoObjects(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(inputDateValidator.validate(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(bookingService.hasDuplicateBookings(listOfNotCorrectBookingDto))
                .thenReturn(false);

        when(bookingDto.getRoom()).thenReturn(room);
        when(bookingDto.getDateStartTime()).thenReturn(startDate);
        when(bookingDto.getDateEndTime()).thenReturn(endDate);
        when(bookingDto.getRecurrentId()).thenReturn(recurrentId);
        when(bookingDto.getId()).thenReturn(bookingId);

        when(bookingService.hasAvailablePlacesInTheRoom(any(BookingsCharacteristics.class), eq(1)))
                .thenReturn(false);

        assertFalse("failure(Not available places) - result should be false", bookingValidator
                .isValidToInsert(listOfNotCorrectBookingDto));

        assertEquals("failure(Is valid) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Not available places)", ValidationConstants.NO_DAYS_FOR_BOOKING,
               bookingValidator.getErrors().get(0));

        ArgumentCaptor<BookingsCharacteristics> characteristics =
                ArgumentCaptor.forClass(BookingsCharacteristics.class);
        ArgumentCaptor<Integer> numOfKids =
                ArgumentCaptor.forClass(Integer.class);

        verify(bookingService)
                .hasAvailablePlacesInTheRoom(characteristics.capture(), numOfKids.capture());

        assertEquals("number of room must be 1", 1, characteristics.getValue().getRooms().size());
        assertEquals("the rooms are not match", room, characteristics.getValue().getRooms().get(0));
        assertEquals("number of dates must be 2", 2, characteristics.getValue().getDates().length);
        assertEquals("the start dates are not match", startDate, characteristics.getValue()
                .getStartDateOfBookings());
        assertEquals("the end dates are not match", endDate, characteristics.getValue()
                .getEndDateOfBookings());
        assertEquals("number of id of bookings must be 1", 1, characteristics.getValue()
                .getIdsOfBookings().size());
        assertEquals("ids of bookings are not match", bookingId, characteristics
                .getValue().getIdsOfBookings().get(0));
        assertEquals("number of recurrent id must be 1", 1, characteristics.getValue()
                .getRecurrentIdsOfBookings().size());
        assertEquals("recurrent ids are not match", recurrentId, characteristics.getValue()
                .getRecurrentIdsOfBookings().get(0));
        assertEquals("the number of kids are not match", 1, (int)numOfKids.getValue());
        assertEquals("number of children must be 0", 0, characteristics.getValue()
                .getChildren().size());
        assertEquals("number of user must be 0", 0, characteristics.getValue().getUsers().size());
        assertEquals("number of bookings states must be 0", 0, characteristics.getValue()
                .getBookingsStates().size());

    }

    @Test
    public void testIsValidToInsertFullValid() {
        List<BookingDto> listOfCorrectBookingDto = Collections.singletonList(bookingDto);
        validBookingDto();

        when(bookingService.normalizeBookingDtoObjects(listOfCorrectBookingDto))
                .thenReturn(true);
        when(inputDateValidator.validate(listOfCorrectBookingDto))
                .thenReturn(true);
        when(bookingService.hasDuplicateBookings(listOfCorrectBookingDto))
                .thenReturn(false);
        when(bookingService.hasAvailablePlacesInTheRoom(any(BookingsCharacteristics.class), eq(1)))
                .thenReturn(true);

        assertTrue("failure(Is valid) - result should be true", bookingValidator
                .isValidToInsert(listOfCorrectBookingDto));

        assertEquals("failure(Is valid) - size should be 0", 0,
                bookingValidator.getErrors().size());

    }

    @Test
    public void isValidToUpdateNotCorrectData() {
        List<BookingDto> listOfNotCorrectBookingDto = Collections.singletonList(bookingDto);
        validBookingDto();

        when(bookingService.normalizeBookingDtoObjects(listOfNotCorrectBookingDto))
                .thenReturn(false);

        assertFalse("failure(Not correct data) - result should be false", bookingValidator
                .isValidToUpdate(listOfNotCorrectBookingDto));

        assertEquals("failure(Not correct dates) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Not correct dates)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void isValidToUpdateNotCorrectDates() {
        List<BookingDto> listOfNotCorrectBookingDto = Collections.singletonList(bookingDto);
        String notValidDateErrorMessage = "Error! Not valid date!";
        List<String> notValidDateErrorMessageList =
                Collections.singletonList(notValidDateErrorMessage);
        validBookingDto();

        when(inputDateValidator.getErrors())
                .thenReturn(notValidDateErrorMessageList);
        when(bookingService.normalizeBookingDtoObjects(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(inputDateValidator.validate(listOfNotCorrectBookingDto))
                .thenReturn(false);

        assertFalse("failure(Not correct dates) - result should be false", bookingValidator
                .isValidToUpdate(listOfNotCorrectBookingDto));

        assertEquals("failure(Not correct dates) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Not correct dates)", notValidDateErrorMessage,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void isValidToUpdateHasDuplicateBookings() {
        List<BookingDto> listOfNotCorrectBookingDto = Collections.singletonList(bookingDto);
        validBookingDto();

        when(bookingService.normalizeBookingDtoObjects(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(inputDateValidator.validate(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(bookingService.hasDuplicateBookings(listOfNotCorrectBookingDto))
                .thenReturn(true);

        assertFalse("failure(Has duplicate bookings) - result should be false", bookingValidator
                .isValidToUpdate(listOfNotCorrectBookingDto));

        assertEquals("failure(Has duplicate bookings) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Has duplicate bookings)",
                ValidationConstants.DUPLICATE_BOOKING_MESSAGE, bookingValidator.getErrors().get(0));

    }

    @Test
    public void isValidToUpdateNotAvailablePlaces() {
        List<BookingDto> listOfNotCorrectBookingDto = Collections.singletonList(bookingDto);
        validBookingDto();

        when(bookingService.normalizeBookingDtoObjects(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(inputDateValidator.validate(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(bookingService.hasDuplicateBookings(listOfNotCorrectBookingDto))
                .thenReturn(false);
        when(bookingService.hasAvailablePlacesInTheRoom(any(BookingsCharacteristics.class), eq(1)))
                .thenReturn(false);

        assertFalse("failure(Not available places) - result should be false", bookingValidator
                .isValidToUpdate(listOfNotCorrectBookingDto));

        assertEquals("failure(Not available places) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Not available places)", ValidationConstants.NO_DAYS_FOR_BOOKING,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void isValidToUpdateNotExistsEntity() {
        List<BookingDto> listOfNotCorrectBookingDto = Collections.singletonList(bookingDto);
        validBookingDto();

        when(bookingService.normalizeBookingDtoObjects(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(inputDateValidator.validate(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(bookingService.hasDuplicateBookings(listOfNotCorrectBookingDto))
                .thenReturn(false);
        when(bookingService.hasAvailablePlacesInTheRoom(any(BookingsCharacteristics.class), eq(1)))
                .thenReturn(true);
        when(bookingService.findEntityById(any())).thenReturn(null);

        assertFalse("failure(Not existed entity) - result should be false", bookingValidator
                .isValidToUpdate(listOfNotCorrectBookingDto));

        assertEquals("failure(Not existed entity) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Not existed entity)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void isValidToUpdateThrowException() {
        List<BookingDto> listOfNotCorrectBookingDto = Collections.singletonList(bookingDto);
        validBookingDto();

        ResourceNotFoundException thrownException = mock(ResourceNotFoundException.class);

        when(bookingService.normalizeBookingDtoObjects(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(inputDateValidator.validate(listOfNotCorrectBookingDto))
                .thenReturn(true);
        when(bookingService.hasDuplicateBookings(listOfNotCorrectBookingDto))
                .thenReturn(false);
        when(bookingService.hasAvailablePlacesInTheRoom(any(BookingsCharacteristics.class), eq(1)))
                .thenReturn(true);
        when(bookingService.findEntityById(any())).thenThrow(thrownException);

        assertFalse("failure(Thrown Exception) - result should be false", bookingValidator
                .isValidToUpdate(listOfNotCorrectBookingDto));

        verify(logger, times(1)).error(any());

        assertEquals("failure(Thrown Exception) - size should be 1", 1,
                bookingValidator.getErrors().size());

        assertEquals("failure(Thrown Exception)", ValidationConstants.COMMON_ERROR_MESSAGE,
                bookingValidator.getErrors().get(0));

    }

    @Test
    public void isValidToUpdateFullValid() {
        List<BookingDto> listOfCorrectBookingDto = Collections.singletonList(bookingDto);
        Booking bookingMock = mock(Booking.class);
        validBookingDto();

        when(bookingService.normalizeBookingDtoObjects(listOfCorrectBookingDto))
                .thenReturn(true);
        when(inputDateValidator.validate(listOfCorrectBookingDto))
                .thenReturn(true);
        when(bookingService.hasDuplicateBookings(listOfCorrectBookingDto))
                .thenReturn(false);
        when(bookingService.hasAvailablePlacesInTheRoom(any(BookingsCharacteristics.class), eq(1)))
                .thenReturn(true);
        when(bookingService.findEntityById(any())).thenReturn(bookingMock);

        assertTrue("failure(Is full valid) - result should be false", bookingValidator
                .isValidToUpdate(listOfCorrectBookingDto));

        assertEquals("failure(Is full valid) - size should be 0", 0,
                bookingValidator.getErrors().size());

    }

    private BookingDto validBookingDto() {
        when(bookingDto.getUserId()).thenReturn(1L);
        when(bookingDto.getRoomId()).thenReturn(1L);
        when(bookingDto.getKidId()).thenReturn(1L);
        when(bookingDto.getComment()).thenReturn("valid comment");
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:00:00");
        when(bookingDto.getEndTime()).thenReturn("2018-02-27T15:00:00");
        return bookingDto;
    }

}
