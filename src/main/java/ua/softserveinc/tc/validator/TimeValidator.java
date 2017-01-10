package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.RoomDto;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * Created by TARAS on 29.06.2016.
 */
@Component
public class TimeValidator implements Validator {

    @Autowired
    private RoomService roomService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RoomDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomDto room = (RoomDto) target;

        LocalTime startTime = LocalTime.parse(room.getWorkingHoursStart());
        LocalTime endTime = LocalTime.parse(room.getWorkingHoursEnd());

        if (startTime.isAfter(endTime)) {
            errors.rejectValue(ValidationConstants.TIME_FIELD, ValidationConstants.TIME_IS_NOT_VALID);
        }
    }

    /**
     * Verify if the date matches the date regex
     * @param date date that will be checked
     * @return boolean value with result of verifing
     */
    public boolean validateDateFormat(String date) {
        return date.matches(ValidationConstants.DATE_REGEX);
    }

    /**
     * Verify if start date is not later then end date
     * @param startTime start date value
     * @param endTime end date value
     * @return boolean balue the result of verifing if the start date isn`t later then end date
     * @throws ParseException throws if date format is wrong
     */
    public boolean validateDate(String startTime, String endTime) throws ParseException {
        if(validateDateFormat(startTime) && validateDateFormat(endTime)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateConstants.SHORT_DATE_FORMAT);
            Date startDate = simpleDateFormat.parse(startTime);
            Date endDate = simpleDateFormat.parse(endTime);

            return !endDate.before(startDate);
        }

        return false;
    }

    /**
     * verify if start booking time is in the range of
     * room working time
     * @param bookingDto booking that will be verified
     * @return true if start booking time is in the range of room working times
     * otherway return false
     */
    public boolean validateRoomTime(BookingDto bookingDto) {
        LocalTime dateStartTimeWorking = LocalTime.parse(roomService
                .findById(bookingDto.getRoomId()).getWorkingHoursStart());
        LocalTime dateEndTimeWorking = LocalTime .parse(roomService
                .findById(bookingDto.getRoomId()).getWorkingHoursEnd());
        LocalTime  startTime = LocalTime.parse(bookingDto.getStartTime());

        return dateStartTimeWorking.isBefore(startTime) && dateEndTimeWorking.isAfter(startTime);
    }

    /**
     * verify if booking start time isn`t later than booking end time
     * @param target booking object that will be verified
     * @return return true if start time isn`t later than end time
     */
    public boolean validateBooking(Object target) {
        BookingDto booking = (BookingDto) target;
        LocalTime startTime = LocalDateTime.parse(booking.getStartTime()).toLocalTime();
        LocalTime endTime = LocalDateTime.parse(booking.getEndTime()).toLocalTime();

        return !startTime.isAfter(endTime);
    }
}
