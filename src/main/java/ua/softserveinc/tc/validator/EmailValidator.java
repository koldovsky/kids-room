package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.dto.EmailWrapper;
import ua.softserveinc.tc.service.UserService;

/**
 * Created by Chak on 25.05.2016.
 */

@Component
public class EmailValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return EmailWrapper.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EmailWrapper emailWrapper = (EmailWrapper)o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "registration.empty");

        if (userService.getUserByEmail(emailWrapper.getEmail()) == null) {
            errors.rejectValue("email", "user.notExist");
        }

    }
}
