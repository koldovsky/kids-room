package ua.softserveinc.tc.validator;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserveinc.tc.constants.UtilConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.Log;

/*
 * Created by Sviatoslav Hryb on 27-Dec-16.
 */
@Component
public class RecurrentBookingValidatorImpl implements RecurrentBookingValidator {

    @Log
    private Logger log;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingValidator bookingValidator;

    private final List<String> errors = new ArrayList<>();

    @Override
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    @Override
    public boolean isValidToInsert(List<BookingDto> dto) {
        boolean result = true;
        errors.clear();

        if (dto == null || dto.isEmpty() || dto.get(0).getDaysOfWeek() == null
                || !hasCorrectDaysOfWeek(dto)) {
            errors.add(ValidationConstants.VALIDATION_NOT_CORRECT_USAGE);

            result = false;
        } else if (!bookingValidator.isValidToInsert(dto)) {
                errors.add(bookingValidator.getErrors().get(0));

                result = false;
        }

        return result;
    }

    @Override
    public boolean isValidToUpdate(List<BookingDto> listDto) {
        boolean result = true;
        errors.clear();

        if(!isValidToInsert(listDto)) {

            result = false;
        } else {
            BookingDto singleDto = listDto.get(0);
            Booking booking = null;
            try {
                booking = bookingService.findEntityById(singleDto.getId());
            } catch (ResourceNotFoundException e) {
                log.error("Not existed booking entity");
            }
            if (booking == null || !booking.getRecurrentId().equals(singleDto.getRecurrentId())
                    || booking.getBookingState() != BookingState.BOOKED) {
                errors.add(ValidationConstants.VALIDATION_NOT_CORRECT_USAGE);

                result = false;
            }
        }

        return result;
    }

    /*
     * Checks if all of the BookingDto from the given list has correct days of week.
     *
     * @param dtoList the given list
     * @return true if all of the BookingDto has correct days of week, otherwise - false
     */
    private boolean hasCorrectDaysOfWeek(List<BookingDto> dtoList) {

        return Arrays.stream(dtoList.get(0).getDaysOfWeek().trim()
                .split(UtilConstants.WHITE_SPACE_REGEXP)).allMatch(
                weekDay -> DateUtil.getDayOfWeek(weekDay) != null);
    }
}
