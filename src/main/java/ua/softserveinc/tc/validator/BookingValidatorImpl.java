package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.DateUtil;

import java.util.Date;

@Component("bookingValidator")
public class BookingValidatorImpl implements BookingValidator {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChildService childService;

    @Autowired
    private RoomService roomService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Booking.class.equals(aClass);
    }
    /**
     * Method contains validation logic
     *
     * @param o      Object to be validated
     * @param errors An object to store validation errors
     */
    @Override
    public void validate(Object o, Errors errors) {
        BookingDto bookingDto = (BookingDto) o;

        ValidationUtils.rejectIfEmpty(errors, ValidationConstants.START_TIME, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors, ValidationConstants.END_TIME, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors, ValidationConstants.USER_ID, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors, ValidationConstants.KID_ID, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors, ValidationConstants.ROOM_ID, ValidationConstants.EMPTY_FIELD_MSG);

        Date startTime = new Date();
        Date endTime = new Date();
        try {
            startTime = DateUtil.toDateISOFormat(bookingDto.getStartTime());
            endTime = DateUtil.toDateISOFormat(bookingDto.getEndTime());
        } catch (ResourceNotFoundException e){
            errors.rejectValue(ValidationConstants.TIME_FIELD, ValidationConstants.DATE_ERROR_MSG);
        }

        if (startTime.after(endTime)) {
            errors.rejectValue(ValidationConstants.TIME_FIELD, ValidationConstants.END_TIME_BEFORE_START_TIME);
        }

        if ( userService.findByIdTransactional(bookingDto.getUserId()) == null) {
            errors.rejectValue(ValidationConstants.EMAIL, ValidationConstants.USER_NOT_EXIST);
        }

        if (childService.findByIdTransactional(bookingDto.getKidId()) == null) {
            errors.rejectValue(ValidationConstants.EMAIL, ValidationConstants.CHILD_NOT_EXIST);
        }
        Room room = roomService.findByIdTransactional(bookingDto.getRoomId());
        if (room == null) {
            errors.rejectValue(ValidationConstants.EMAIL, ValidationConstants.ROOM_NOT_EXIST);
        }
    }
}
