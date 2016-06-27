package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import ua.softserveinc.tc.constants.ValidationConstants;
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
        ValidationUtils.rejectIfEmpty(errors, ValidationConstants.FIRST_NAME, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors, ValidationConstants.LAST_NAME, ValidationConstants.EMPTY_FIELD_MSG);


        if (!Pattern.compile(ValidationConstants.PASSWORD_REGEX)
                .matcher(user.getPassword())
                .matches()) {
            errors.rejectValue(ValidationConstants.PASSWORD, ValidationConstants.PASSWORD_ERROR_MSG);
        }

        if (!Pattern.compile(ValidationConstants.PHONE_NUMBER_REGEX)
                .matcher(user.getPhoneNumber())
                .matches()) {
            errors.rejectValue(ValidationConstants.PHONE_NUMBER, ValidationConstants.PHONE_NUMBER_ERROR_MSG);
        }
//  This code will be used when project will deploy
//        if (!Pattern.compile(ValidationConstants.EMAIL_REGEX)
//                .matcher(user.getEmail())
//                .matches()) {
//            errors.rejectValue(ValidationConstants.EMAIL, ValidationConstants.EMAIL_ERROR_MSG);
//        }

        if (!user.getPassword().equals(user.getConfirm())) {
            errors.rejectValue(ValidationConstants.CONFIRM, ValidationConstants.NOT_CONFIRMED_MSG);
        }

        if (userService.getUserByEmail(user.getEmail()) != null) {
            errors.rejectValue(ValidationConstants.EMAIL, ValidationConstants.EMAIL_ALREADY_IN_USE_MSG);
        }
    }

    public void validateEmail(Object o, Errors errors) {
        String email = (String) o;
        if (userService.getUserByEmail(email) == null) {
            errors.rejectValue(ValidationConstants.EMAIL, ValidationConstants.USER_NOT_EXIST);
        }
    }

    public void validatePassword(Object o, Errors errors) {
        User user = (User) o;
        if (!Pattern.compile(ValidationConstants.PASSWORD_REGEX)
                .matcher(user.getPassword())
                .matches()) {
            errors.rejectValue(ValidationConstants.PASSWORD, ValidationConstants.PASSWORD_ERROR_MSG);
        }

        if (!user.getPassword().equals(user.getConfirm())) {
            errors.rejectValue(ValidationConstants.CONFIRM, ValidationConstants.NOT_CONFIRMED_MSG);
        }
    }

    public void validateIfEmailExist(Object o, Errors errors) {
        User user = (User) o;

        if (userService.getUserByEmail(user.getEmail()) != null) {
            errors.rejectValue(ValidationConstants.EMAIL, ValidationConstants.EMAIL_ALREADY_IN_USE_MSG);
        }
    }

    public void validateManagerEmail(Object target, Errors errors) {
        User manager = (User) target;

        if (!Pattern.compile(ValidationConstants.SIMPLE_EMAIL_REGEX)
                .matcher(manager.getEmail()).matches()) {
            errors.rejectValue(ValidationConstants.EMAIL, ValidationConstants.EMAIL_NOT_VALID);
        }
        if (this.userService.getUserByEmail(manager.getEmail()) != null) {
            errors.rejectValue(ValidationConstants.EMAIL, ValidationConstants.EMAIL_ALREADY_IN_USE_MSG);
        }
    }
}