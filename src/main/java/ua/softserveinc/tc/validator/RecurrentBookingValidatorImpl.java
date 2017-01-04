package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ua.softserveinc.tc.constants.*;
import ua.softserveinc.tc.dto.BookingDto;

import java.util.*;
import java.util.regex.Pattern;

import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.util.DateUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Validator for recurrent bookings. For more information  see:
 * {@link RecurrentBookingValidator}
 * <p>
 * Created by Sviatoslav Hryb on 27-Dec-16.
 */
@Component
public class RecurrentBookingValidatorImpl implements RecurrentBookingValidator {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    private final List<String> errors = new ArrayList<>();

    private boolean hasCorrectData(List<BookingDto> dtoList) {

        return bookingService.normalizeBookingDtoObjects(dtoList);
    }

    private boolean hasCorrectDaysOfWeek(List<BookingDto> dtoList) {

        return Arrays.stream(dtoList.get(0).getDaysOfWeek()
                .split(UtilConstants.WHITE_SPACE_REGEXP)).allMatch(
                weekDay -> DateUtil.getDayOfWeek(weekDay) != null);
    }

    private boolean hasNull(List<BookingDto> dtoList) {

        return dtoList.stream().anyMatch(dto -> {
            boolean result = false;
            if (dto.getStartTime() == null || dto.getEndTime() == null || dto.getDaysOfWeek() == null
                    || dto.getUserId() == null || dto.getRoomId() == null || dto.getKidId() == null
                    || dto.getComment() == null) {
                result = true;
            }

            return result;
        });
    }

    private boolean hasCorrectTimeFormats(List<BookingDto> dtoList) {

        return dtoList.stream().noneMatch(dto -> {
            boolean result = false;
            if (!Pattern.matches(DateConstants.DATE_T_TIME_REGEXP, dto.getStartTime())
                    || !Pattern.matches(DateConstants.DATE_T_TIME_REGEXP, dto.getEndTime())) {
                result = true;
            }

            return result;
        });
    }

    private boolean isBadTimeRelations(List<BookingDto> dtoList) {

        return dtoList.stream().anyMatch(dto -> {
            boolean result = false;
            Date currentDate = new Date();
            Date startTime = DateUtil.toDateISOFormat(dto.getStartTime());
            Date endTime = DateUtil.toDateISOFormat(dto.getEndTime());
            if (!startTime.before(endTime) || startTime.before(currentDate)) {
                result = true;
            }

            return result;
        });
    }

    private boolean hasDuplicateBooking(List<BookingDto> dtoList) {

        return bookingService.hasDuplicateBookings(dtoList);
    }

    @Override
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    @Override
    public boolean validate(List<BookingDto> dto) {
        boolean result = true;
        errors.clear();
        Locale locale = (Locale) request.getSession().getAttribute(LocaleConstants.SESSION_LOCALE_ATTRIBUTE);
        if (locale == null) {
            locale = request.getLocale();
        }
        if (dto == null || dto.isEmpty() || hasNull(dto) || !hasCorrectData(dto)
                || !hasCorrectDaysOfWeek(dto)) {

            errors.add(messageSource.getMessage(
                    ValidationConstants.VALIDATION_NOT_CORRECT_USAGE, null, locale));
            result = false;
        } else {
            if (!hasCorrectTimeFormats(dto)) {
                errors.add(messageSource.getMessage(
                        ValidationConstants.BAD_TIME_FORMAT, null, locale));

                result = false;
            } else if (isBadTimeRelations(dto)) {
                errors.add(messageSource.getMessage(
                        ValidationConstants.END_TIME_BEFORE_START_TIME, null, locale));

                result = false;
            } else if (hasDuplicateBooking(dto)) {
                errors.add(messageSource.getMessage(
                        ValidationConstants.DUPLICATE_BOOKING_MESSAGE, null, locale));

                result = false;
            }
        }

        return result;
    }
}
