package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import ua.softserveinc.tc.constants.ValidationConst;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.util.regex.Pattern;

/**
 * Created by Nestor on 13.05.2016.
 * A validator-class that handles User objects validation
 */

@Component("userValidator")
public class UserValidatorImpl implements UserValidator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Method contains validation logic
     *
     * @param o      Object to be validated
     * @param errors An object to store validation errors
     */
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmpty(errors, ValidationConst.FIRST_NAME, ValidationConst.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors, ValidationConst.LAST_NAME, ValidationConst.EMPTY_FIELD_MSG);


        if (!Pattern.compile(ValidationConst.PASSWORD_REGEX)
                .matcher(user.getPassword())
                .matches()) {
            errors.rejectValue(ValidationConst.PASSWORD, ValidationConst.PASSWORD_ERROR_MSG);
        }

        if (!Pattern.compile(ValidationConst.PHONE_NUMBER_REGEX)
                .matcher(user.getPhoneNumber())
                .matches()) {
            errors.rejectValue(ValidationConst.PHONE_NUMBER, ValidationConst.PHONE_NUMBER_ERROR_MSG);
        }

//        if (!Pattern.compile(ValidationConst.EMAIL_REGEX)
//                .matcher(user.getEmail())
//                .matches()) {
//            errors.rejectValue(ValidationConst.EMAIL, ValidationConst.EMAIL_ERROR_MSG);
//        }

        if (!user.getPassword().equals(user.getConfirm())) {
            errors.rejectValue(ValidationConst.CONFIRM, ValidationConst.NOT_CONFIRMED_MSG);
        }

        if (userService.getUserByEmail(user.getEmail()) != null) {
            errors.rejectValue(ValidationConst.EMAIL, ValidationConst.EMAIL_ALREADY_IN_USE_MSG);
        }
    }

    public void validateEmail(Object o, Errors errors) {
        String email = (String) o;
        if (userService.getUserByEmail(email) == null) {
            errors.rejectValue(ValidationConst.EMAIL, ValidationConst.USER_NOT_EXIST);
        }
    }

    public void validatePassword(Object o, Errors errors) {
        User user = (User) o;
        if (!Pattern.compile(ValidationConst.PASSWORD_REGEX)
                .matcher(user.getPassword())
                .matches()) {
            errors.rejectValue(ValidationConst.PASSWORD, ValidationConst.PASSWORD_ERROR_MSG);
        }

        if (!user.getPassword().equals(user.getConfirm())) {
            errors.rejectValue(ValidationConst.CONFIRM, ValidationConst.NOT_CONFIRMED_MSG);
        }
    }

}
