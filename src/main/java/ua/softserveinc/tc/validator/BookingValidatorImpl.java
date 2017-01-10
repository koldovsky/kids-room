package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.service.BookingService;

import java.util.ArrayList;
import java.util.List;

/*
 * Written from scratch by Sviatoslav Hryb on 10-Jan-2017.
 */
@Component("bookingValidator")
public class BookingValidatorImpl implements BookingValidator {

    @Autowired
    private BookingService bookingService;

    @Autowired
    InputDateValidatorImpl inputDateValidator;

    private final List<String> errors = new ArrayList<>();

    @Override
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    @Override
    public boolean validate(List<BookingDto> dto) {
        boolean result = true;
        errors.clear();

        if (dto == null || dto.isEmpty() || hasNull(dto)) {
            errors.add(ValidationConstants.VALIDATION_NOT_CORRECT_USAGE);

            result = false;
        } else {
            if (!hasCorrectData(dto)) {
                errors.add(ValidationConstants.VALIDATION_NOT_CORRECT_USAGE);

                result = false;
            } else if (!inputDateValidator.validate(dto)) {
                errors.add(inputDateValidator.getErrors().get(0));

                result = false;
            } else if (hasDuplicateBooking(dto)) {
                errors.add(ValidationConstants.DUPLICATE_BOOKING_MESSAGE);

                result = false;
            }
        }

        return result;
    }

    /*
     * Checks if the any of the BookingDto from the given list has null.
     *
     * @param dtoList the given list
     * @return true if any of the BookingDto has null, otherwise - false
     */
    private boolean hasNull(List<BookingDto> dtoList) {

        return dtoList.stream().anyMatch(dto -> {
            boolean result = false;
            if (dto.getUserId() == null || dto.getRoomId() == null
                    || dto.getKidId() == null || dto.getComment() == null
                    || dto.getStartTime() == null || dto.getEndTime() == null) {

                result = true;
            }

            return result;
        });
    }

    /*
     * Checks if all of the BookingDto from the given list has correct data.
     * The correctness of the data means that object contains correct id of room,
     * id of user and id of child.
     *
     * @param dtoList the given list
     * @return true if all of the BookingDto has correct data, otherwise - false
     */
    private boolean hasCorrectData(List<BookingDto> dtoList) {

        return bookingService.normalizeBookingDtoObjects(dtoList);
    }

    /*
     * Checks if any of the BookingDto objects forms a duplicate bookings.
     *
     * @param dtoList the given list
     * @return true if any of the BookingDto objects forms duplicate bookings,
     * otherwise - false
     */
    private boolean hasDuplicateBooking(List<BookingDto> dtoList) {

        return bookingService.hasDuplicateBookings(dtoList);
    }
}
