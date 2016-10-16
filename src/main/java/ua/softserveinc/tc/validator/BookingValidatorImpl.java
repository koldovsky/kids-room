package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.service.BookingService;

/**
 * Created by sdub on 09.10.2016.
 */
public class BookingValidatorImpl implements BookingValidator {
    @Autowired
    private BookingService bookingService;

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
        Booking booking = (Booking) o;
    }
}
