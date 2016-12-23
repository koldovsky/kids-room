package ua.softserveinc.tc.validator;

import org.junit.Before;
import org.junit.Test;
import ua.softserveinc.tc.dto.BookingDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class ChildValidatorTest {
    private Validator validator;

    @Before
    public void beforeTest() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    public void prereqsMet() {
        ChildValidator childValidator = new ChildValidator();
        Set<ConstraintViolation<ChildValidator>> violations = this.validator.validate(childValidator);
        assertTrue(violations.isEmpty());
    }
}
