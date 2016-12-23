package ua.softserveinc.tc.validator;

import org.junit.Before;
import org.junit.Test;
import ua.softserveinc.tc.dto.ConfigurationDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by melancholiya on 20.12.2016.
 */

public class ConfigValidatorTest {
    private Validator validator;

    @Before
    public void beforeTest() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    public void prereqsMet() {
        ConfigurationDto configurationDto = new ConfigurationDto();
        Set<ConstraintViolation<ConfigurationDto>> violations = this.validator.validate(configurationDto);
        assertTrue(violations.isEmpty());
    }
}
