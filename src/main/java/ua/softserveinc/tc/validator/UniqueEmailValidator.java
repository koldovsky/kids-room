package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import ua.softserveinc.tc.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Chak on 09.05.2016.
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userService == null) {
            return true;
        }
        return userService.getUserByEmail(value) == null;
    }

}