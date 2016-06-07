package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.ApplicationConfigurator;
import ua.softserveinc.tc.constants.ValidationConst;
import ua.softserveinc.tc.entity.Child;

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

        ValidationUtils.rejectIfEmpty(errors, ValidationConst.FIRST_NAME, ValidationConst.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors, ValidationConst.LAST_NAME, ValidationConst.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors, ValidationConst.CHILD_DATE_OF_BIRTH, ValidationConst.EMPTY_FIELD_MSG);

        int age = kidToValidate.getAge();

        if(age < configurator.getKidsMinAge() || age> configurator.getKidsMaxAge()){
            errors.rejectValue(ValidationConst.CHILD_DATE_OF_BIRTH, ValidationConst.DATE_ERROR_MSG);
        }
    }
}
