package ua.softserveinc.tc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.RoomDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

/**
 * Created by TARAS on 29.06.2016.
 */
@Component
public class TimeValidator implements Validator {

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

    public boolean validateBooking(Object target) {
        BookingDto booking = (BookingDto) target;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String startTime=booking.getStartTime().substring(11);
        String endTime=booking.getEndTime().substring(11);

        if (startTime.compareTo(endTime)>0) {
           return false;
        }
        return true;
    }
}
