package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.RoomDto;
import ua.softserveinc.tc.service.RoomService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


@Component
public class TimeValidatorImpl implements TimeValidator {

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

    public boolean validateDateFormat(String date) {

        return date.matches(DateConstants.DATE_REGEXP);
    }

    public boolean validateDateTimeFormat(String dateTime) {

        return dateTime.matches(DateConstants.DATE_T_SPACE_TIME_REGEXP);
    }

    public boolean validateTimeFormat(String time) {

        return time.matches(DateConstants.TIME_REGEX);
    }

    public boolean isStartDateBefore(String startTime, String endTime) throws ParseException {
        boolean isValid = false;
        if (validateDateFormat(startTime) && validateDateFormat(endTime)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateConstants.SHORT_DATE_FORMAT);
            Date startDate = simpleDateFormat.parse(startTime);
            Date endDate = simpleDateFormat.parse(endTime);

            isValid = !endDate.before(startDate);
        }

        return isValid;
    }

    public boolean validateRoomTime(BookingDto bookingDto) {

        LocalTime dateStartTimeWorking = LocalTime.parse(roomService
                .findByIdTransactional(bookingDto.getRoomId()).getWorkingHoursStart());
        LocalTime dateEndTimeWorking = LocalTime.parse(roomService
                .findByIdTransactional(bookingDto.getRoomId()).getWorkingHoursEnd());
        boolean isValid = false;

        if (validateDateTimeFormat(bookingDto.getStartTime()) ||
                validateTimeFormat(bookingDto.getStartTime())) {
            LocalTime startTime = LocalTime.parse(bookingDto.getStartTime());
            if (dateStartTimeWorking.equals(startTime)) {
                isValid = true;
            } else if (dateStartTimeWorking.isBefore(startTime) && dateEndTimeWorking.isAfter(startTime)) {
                isValid = true;
            }
        }

        return isValid;
    }

    public boolean validateBooking(Object target) {
        BookingDto booking = (BookingDto) target;
        LocalTime startTime = LocalDateTime.parse(booking.getStartTime()).toLocalTime();
        LocalTime endTime = LocalDateTime.parse(booking.getEndTime()).toLocalTime();

        return !startTime.isAfter(endTime);
    }
}
