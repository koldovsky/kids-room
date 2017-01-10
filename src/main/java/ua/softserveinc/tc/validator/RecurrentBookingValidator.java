package ua.softserveinc.tc.validator;

import java.util.List;

import ua.softserveinc.tc.dto.BookingDto;

/**
 * Validator for checking if the object of BookingDto
 * has not empty or null fields: startTime, endTime, comment,
 * kidId, roomId, userId, daysOfWeek. Also checks if the endTime
 * is not before startTime and if the startTime is not in the past,
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
     * is not before startTime and if the startTime is not in the past,
     * and if there are not duplicate bookings.
     *
     * @param listDto list of given BookingDto
     * @return true if validation is passed, otherwise - false
     */
    boolean validate(List<BookingDto> listDto);
}
