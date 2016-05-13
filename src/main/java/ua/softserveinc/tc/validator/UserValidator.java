package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.ValidationConst;
import ua.softserveinc.tc.entity.User;

import javax.validation.Valid;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nestor on 13.05.2016.
 */

@Component
public class UserValidator implements Validator{
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);

    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmpty(errors, "firstName",  ValidationConst.EMPTY_FIELD_OR_SPACE);

        if(!Character.isUpperCase(user.getFirstName().charAt(0))){
            errors.rejectValue("firstName",  ValidationConst.NAME_ERROR);
        }

        ValidationUtils.rejectIfEmpty(errors, "lastName",  ValidationConst.EMPTY_FIELD_OR_SPACE);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", ValidationConst.EMPTY_FIELD_OR_SPACE);

        if(!Pattern.compile(ValidationConst.PASSWORD_REGEX)
                .matcher(user.getPassword())
                .matches()){
            errors.rejectValue("password", ValidationConst.PASSWORD_INVALID);
        }

        if(!Pattern.compile(ValidationConst.PHONE_NUMBER_REGEX)
                .matcher(user.getPhoneNumber())
                .matches()){
            errors.rejectValue("phoneNumber", ValidationConst.PHONE_NUMBER_INVALID);
        }


    }
}
