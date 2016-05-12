package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.ChildService;

import java.util.Date;

/**
 * Created by Nestor on 12.05.2016.
 */

@Component
public class ChildValidator implements Validator{
    public static final String INAPPROPRIATE_AGE = "Inappropriate age";
    public static final String EMPTY_FIELD_OR_SPACE = "Field is empty or contains a whitespace";
    public static final String ACCOUNT_REMOVED = "Child's account was deactivated";
    public static final String PARENT_NOT_SET = "Failed to fetch parent's ID";
    public static final String NAME_ERROR = "Error in name";


    @Autowired
    private ChildService childService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Child.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Child kidToValidate = (Child) o;

        //TODO: ці штуки чомусь не працюють
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", EMPTY_FIELD_OR_SPACE);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", EMPTY_FIELD_OR_SPACE);

        Date birth = kidToValidate.getDateOfBirth();

        if(birth == null){
            errors.rejectValue("dateOfBirth", EMPTY_FIELD_OR_SPACE);
        }

        int age = kidToValidate.getAge();
        if(age<Child.MIN_AGE || age>Child.MAX_AGE){
            errors.rejectValue("dateOfBirth", INAPPROPRIATE_AGE);
        }

        if(!kidToValidate.isEnabled()){
            errors.rejectValue("enabled", ACCOUNT_REMOVED);
        }

        if(kidToValidate.getParentId() == null){
            errors.rejectValue("parentId", PARENT_NOT_SET);
        }

        if(!Character.isUpperCase(kidToValidate.getFirstName().charAt(0))){
            errors.rejectValue("firstName", NAME_ERROR);
        }

    }
}
