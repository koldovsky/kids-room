package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.util.ApplicationConfigurator;

import java.util.regex.Pattern;

/**
 * A validator-class that handles Child objects validation
 */

@Component
public class ChildValidator implements Validator{
    public static final int MIN_NAME_CHARACTER = 2;

    @Autowired
    private ApplicationConfigurator configurator;

    @Override
    public boolean supports(Class<?> aClass) {
        return Child.class.equals(aClass);
    }

    /**
     * Method contains validation logic
     *
     * @param o Object to be validated
     * @param errors An object to store validation errors
     */
    @Override
    public void validate(Object o, Errors errors) {
        Child kidToValidate = (Child) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.FIRST_NAME, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.LAST_NAME, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.CHILD_DATE_OF_BIRTH, ValidationConstants.EMPTY_FIELD_MSG);

        if (!Pattern.compile(ValidationConstants.NAME_REGEX)
            .matcher(kidToValidate.getFirstName())
            .matches()) {
            errors.rejectValue(ValidationConstants.FIRST_NAME, ValidationConstants.NAME_ERROR_MSG);
        }

        if (!Pattern.compile(ValidationConstants.NAME_REGEX)
            .matcher(kidToValidate.getLastName())
            .matches()) {
            errors.rejectValue(ValidationConstants.LAST_NAME, ValidationConstants.NAME_ERROR_MSG);
        }

        String fName = kidToValidate.getFirstName();
        String lName = kidToValidate.getLastName();
        if(fName.length() < MIN_NAME_CHARACTER) {
            errors.rejectValue(ValidationConstants.FIRST_NAME, ValidationConstants.NAME_NOT_EDITTED);
        }
        if(lName.length() < MIN_NAME_CHARACTER) {
            errors.rejectValue(ValidationConstants.LAST_NAME, ValidationConstants.NAME_NOT_EDITTED);
        }

        int age = kidToValidate.getAge();
        if(age < configurator.getKidsMinAge() || age > configurator.getKidsMaxAge()){
            errors.rejectValue(ValidationConstants.CHILD_DATE_OF_BIRTH, ValidationConstants.DATE_ERROR_MSG);
        }

        String comment = kidToValidate.getComment();
        if (comment.length() > ValidationConstants.KID_COMMENT_MAX_LENGHT) {
            errors.rejectValue(ValidationConstants.COMMENT, ValidationConstants.COMMENT_ERROR_MSG);
        }

        String firstName = kidToValidate.getFirstName();
        if (firstName.length() > ValidationConstants.MAX_NAME_CHARACTER) {
            errors.rejectValue(ValidationConstants.FIRST_NAME, ValidationConstants.FIRSTNAME_ERROR_MSG);
        }

        String lastName = kidToValidate.getLastName();
        if (lastName.length() > ValidationConstants.MAX_NAME_CHARACTER) {
            errors.rejectValue(ValidationConstants.LAST_NAME, ValidationConstants.LASTNAME_ERROR_MSG);
        }
    }
}
