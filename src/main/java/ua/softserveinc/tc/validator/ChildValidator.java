package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.ValidationConst;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.ChildService;

import java.util.Date;

/**
 * Created by Nestor on 12.05.2016.
 */

@Component
public class ChildValidator implements Validator{




    @Override
    public boolean supports(Class<?> aClass) {
        return Child.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Child kidToValidate = (Child) o;

        //TODO: ці штуки чомусь не працюють
        ValidationUtils.rejectIfEmpty(errors, "firstName", ValidationConst.EMPTY_FIELD_OR_SPACE);
        ValidationUtils.rejectIfEmpty(errors, "lastName", ValidationConst.EMPTY_FIELD_OR_SPACE);

        Date birth = kidToValidate.getDateOfBirth();

        if(birth == null){
            errors.rejectValue("dateOfBirth",  ValidationConst.EMPTY_FIELD_OR_SPACE);
        }

        int age = kidToValidate.getAge();
        if(age<Child.MIN_AGE || age>Child.MAX_AGE){
            errors.rejectValue("dateOfBirth",  ValidationConst.INAPPROPRIATE_AGE);
        }

        if(!kidToValidate.isEnabled()){
            errors.rejectValue("enabled",  ValidationConst.ACCOUNT_REMOVED);
        }

        if(kidToValidate.getParentId() == null){
            errors.rejectValue("parentId",  ValidationConst.PARENT_NOT_SET);
        }

        if(!Character.isUpperCase(kidToValidate.getFirstName().charAt(0))){
            errors.rejectValue("firstName",  ValidationConst.NAME_ERROR);
        }

    }
}
