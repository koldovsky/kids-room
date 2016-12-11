package ua.softserveinc.tc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.util.Log;
import org.slf4j.Logger;

/**
 * Validator for checking if the given Object o is a String
 * and represents a number.
 * If validation is failed then relevant message is logging to the log.
 *
 * Created by Sviatoslav Hryb on 07-Dec-16.
 */
@Component
public class NumberRequestValidatorImpl implements NumberRequestValidator {
    @Log
    private Logger log;

    @Override
    public void validate(Object o, Errors errors) {
        if (o == null || o.getClass() != String.class) {
            log.error("Validator error! Entered wrong object for validation");
            errors.rejectValue(ValidationConstants.IMAGE,
                    ValidationConstants.IMAGE_VALIDATION_NOT_CORRECT_USAGE);
        } else if (!((String)o).matches("\\d+")) {
            log.error("Validator error! Entered string is not represent a number");
            errors.rejectValue(ValidationConstants.IMAGE,
                    ValidationConstants.IMAGE_VALIDATION_NOT_CORRECT_USAGE);
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return String.class == aClass;
    }
}
