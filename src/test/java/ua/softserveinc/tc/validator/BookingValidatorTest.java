package ua.softserveinc.tc.validator;

import org.junit.Before;
import org.junit.Test;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by meltnacholiya on 20.12.2016.
 */
public class BookingValidatorTest {

    private Validator validator;

    @Before
    public void beforeTest() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    public void prereqsMet() {
        BookingDto bookingDto = new BookingDto();
        Set<ConstraintViolation<BookingDto>> violations = this.validator.validate(bookingDto);
        assertTrue(violations.isEmpty());
    }

}
