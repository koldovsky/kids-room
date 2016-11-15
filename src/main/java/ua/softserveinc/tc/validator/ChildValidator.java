package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.util.ApplicationConfigurator;

/**
 * Created by Nestor on 12.05.2016.
 * A validator-class that handles Child objects validation
 */

@Component
public class ChildValidator implements Validator{
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

        ValidationUtils.rejectIfEmpty(errors, ValidationConstants.FIRST_NAME, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors, ValidationConstants.LAST_NAME, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors, ValidationConstants.CHILD_DATE_OF_BIRTH, ValidationConstants.EMPTY_FIELD_MSG);


/**
 * check if the first and last names are corrent(if there is only 1 letter)
 * Error messages appear. "First Name" and "Last Name" fields are not edited.
 */
        String fName = kidToValidate.getFirstName();
        String lName = kidToValidate.getLastName();

        if ((fName.length() < 2) || (lName.length() < 2)) {

            if(fName.length() < 2) {
                errors.rejectValue(ValidationConstants.FIRST_NAME, ValidationConstants.NAME_NOT_EDITTED);
            } else if(lName.length() < 2) {
                errors.rejectValue(ValidationConstants.LAST_NAME, ValidationConstants.NAME_NOT_EDITTED);
            }
        }   // end of the if statement

        int age = kidToValidate.getAge();

        if(age < configurator.getKidsMinAge() || age> configurator.getKidsMaxAge()){
            errors.rejectValue(ValidationConstants.CHILD_DATE_OF_BIRTH, ValidationConstants.DATE_ERROR_MSG);
        }

        String comment = kidToValidate.getComment();
        if (comment.length() > 250){errors.rejectValue("comment", "registration.kid.comment");}
    }
}
