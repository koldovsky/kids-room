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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public boolean validateDateFormat(String date) {
        return date.matches(ValidationConstants.DATE_REGEX);
    }

    public boolean validateDate(String startTime, String endTime) throws ParseException {
        if(validateDateFormat(startTime) && validateDateFormat(endTime)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateConstants.SHORT_DATE_FORMAT);
            Date startDate = simpleDateFormat.parse(startTime);
            Date endDate = simpleDateFormat.parse(endTime);

            return !endDate.before(startDate);
        }

        return false;
    }

    public boolean validateRoomTime(BookingDto bookingDto) {
        LocalTime dateStartTimeWorking = LocalTime.parse(roomService
                .findByIdTransactional(bookingDto.getRoomId()).getWorkingHoursStart());
        LocalTime dateEndTimeWorking = LocalTime .parse(roomService
                .findByIdTransactional(bookingDto.getRoomId()).getWorkingHoursEnd());
        LocalTime  startTime = LocalTime.parse(bookingDto.getStartTime());

        return dateStartTimeWorking.isBefore(startTime) && dateEndTimeWorking.isAfter(startTime);
    }

    public boolean validateBooking(Object target) {
        BookingDto booking = (BookingDto) target;
        String startTime = booking.getStartTime().substring(11);
        String endTime = booking.getEndTime().substring(11);

        return startTime.compareTo(endTime) <= 0;
    }
}
