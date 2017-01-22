package ua.softserveinc.tc.validator;

import ua.softserveinc.tc.dto.BookingDto;

import java.util.List;

/**
 * Validator to validation the given list of BookingDto objects for correctness.
 * The correctness means that start and end date are not null, have correct
 * format, start date are not in the past, end date are not before start date,
 * start and end dates are in the range of workings hours. To correct working of
 * this class, each of the given dto object must has correct room id. Otherwise method
 * isValidToInsert will return false and produces not correct error message. Each BookingDto
 * in list must have the same user, room and dates.
 */
public interface InputDateTimeValidator {
    /**
     * Returns the list of errors messages that occurred. If there are
     * no errors then empty list will be returned
     *
     * @return list of errors messages
     */
    List<String> getErrors();

    /**
     * Checks the given list of BookingDto objects for correctness.
     * The correctness means that start and end date are not null, have correct
     * format, start date are not in the past, end date are not before start date,
     * start and end dates are in the range of workings hours.
     *
     * @param listDto list of given BookingDto
     * @return true if validation is passed, otherwise - false
     */
    boolean validate(List<BookingDto> listDto);
}
