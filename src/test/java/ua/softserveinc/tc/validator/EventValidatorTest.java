package ua.softserveinc.tc.validator;

import org.junit.Before;
import org.junit.Test;
import ua.softserveinc.tc.dto.ConfigurationDto;
import ua.softserveinc.tc.dto.EventDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by melancholiya on 20.12.2016.
 */
public class EventValidatorTest {
    private Validator validator;

    @Before
    public void beforeTest() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    public void prereqsMet() {
        EventDto eventDto = new EventDto();
        Set<ConstraintViolation<EventDto>> violations = this.validator.validate(eventDto);
        assertTrue(violations.isEmpty());
    }
}
