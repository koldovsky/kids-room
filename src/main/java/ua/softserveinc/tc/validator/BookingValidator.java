package ua.softserveinc.tc.validator;

import ua.softserveinc.tc.dto.BookingDto;

import java.util.List;

/**
 * Validator for checking if the object of BookingDto
 * has not empty or null fields: startTime, endTime, comment,
 * kidId, roomId, userId. Also checks if the endTime
 * is not before startTime, the startTime is not in the past,
 * start and end time is not out of range of working hours
 * and if there are not duplicate bookings.
 *
 * Rewritten by Sviatoslav Hryb on 10.01.2017.
 */
public interface BookingValidator{

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
     * kidId, roomId, userId. Also checks if the endTime
     * is not before startTime, the startTime is not in the past,
     * start and end time is not out of range of working hours
     * and if there are not duplicate bookings
     *
     * @param listDto list of given BookingDto
     * @return true if validation is passed, otherwise - false
     */
    boolean validate(List<BookingDto> listDto);
}
