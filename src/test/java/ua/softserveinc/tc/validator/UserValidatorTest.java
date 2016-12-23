package ua.softserveinc.tc.validator;

import org.junit.Before;
import org.junit.Test;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by melancholiya on 20.12.2016.
 */
public class UserValidatorTest {

    private Validator validator;

    @Before
    public void beforeTest() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    public void prereqsMet() {
        User user = new User();
        Set<ConstraintViolation<User>> violations = this.validator.validate(user);
        assertTrue(violations.isEmpty());
    }

}
