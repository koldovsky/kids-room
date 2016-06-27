package ua.softserveinc.tc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Chack on 09.06.2016.
 */
public interface UserValidator extends Validator{

    void validateEmail(Object o, Errors errors);

    void validatePassword(Object o, Errors errors);

    void validateIfEmailExist(Object o, Errors errors);

    void validateManagerEmail(Object target, Errors errors);
}
