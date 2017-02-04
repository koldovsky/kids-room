package ua.softserveinc.tc.validator;

import org.springframework.validation.Validator;
import ua.softserveinc.tc.dto.BookingDto;

import java.text.ParseException;

public interface TimeValidator extends Validator {

    /**
     * Verify if the date matches the date regex
     * @param date date that will be checked
     * @return boolean value with result of verifing
     */
    boolean validateDateFormat(String date);

    /**
     * Verify id the dateTime matches the dateTime regex
     * @param dateTime dateTime that will be checked
     * @return result of validation
     */
    boolean validateDateTimeFormat(String dateTime);

    /**
     * Verify id the time matches the time regex
     * @param time time that will be checked
     * @return result of validation
     */
    boolean validateTimeFormat(String time);

    /**
     * Verify if start date is not later then end date
     * @param startTime start date value
     * @param endTime end date value
     * @return boolean balue the result of verifing if the start date isn`t later then end date
     * @throws ParseException throws if date format is wrong
     */
    boolean isStartDateBefore(String startTime, String endTime) throws ParseException;

    /**
     * verify if start booking time is in the range of
     * room working time
     * @param bookingDto booking that will be verified
     * @return true if start booking time is in the range of room working times
     * otherway return false
     */
    boolean validateRoomTime(BookingDto bookingDto);

    /**
     * verify if booking start time isn`t later than booking end time
     * @param target booking object that will be verified
     * @return return true if start time isn`t later than end time
     */
    boolean validateBooking(Object target);

}
