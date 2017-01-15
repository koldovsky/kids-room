package ua.softserveinc.tc.validator;

import java.util.List;

import ua.softserveinc.tc.dto.BookingDto;

/**
 * Validator for checking if the object of BookingDto
 * has not empty or null fields: startTime, endTime, comment,
 * kidId, roomId, userId, daysOfWeek. Also checks if the endTime
 * is not before startTime, the startTime is not in the past,
 * start and end time is not out of range of working hours
 * and if there are not duplicate bookings.
 *
 * Created by Sviatoslav Hryb on 27-Dec-16.
 */
public interface RecurrentBookingValidator {

    /**
     * Returns the list of errors messages that occurred. If there are
     * no errors then empty list will be returned
     *
     * @return list of errors messages
     */
    List<String> getErrors();

    /**
     * Checks if the list of BookingDto objects is not null or empty,
     * has not empty or null fields: startTime, endTime, comment,
     * kidId, roomId, userId, daysOfWeek. Also checks if the endTime
     * is not before startTime, the startTime is not in the past,
     * start and end time is not out of range of working hours
     * and if there are not duplicate bookings
     *
     * @param listDto list of given BookingDto
     * @return true if validation is passed, otherwise - false
     */
    boolean isValidToInsert(List<BookingDto> listDto);

    /**
     * Checks if the list of BookingDto objects is valid for updating.
     *
     * @param listDto the given list of BookingDto objects
     * @return true if given list is valid, otherwise - false
     */
    boolean isValidToUpdate(List<BookingDto> listDto);

    /**
     * Checks if the list of BookingDto objects is valid for removing.
     *
     * @param listDto the given list of BookingDto objects
     * @return true if given list is valid, otherwise - false
     */
    boolean isValidToRemove(List<BookingDto> listDto);
}
