package ua.softserveinc.tc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public interface UserValidator extends Validator{

    void validateEmail(Object o, Errors errors);

    void validatePassword(Object o, Errors errors);

    void validateIfEmailExist(Object o, Errors errors);

    void validateManagerEmail(Object target, Errors errors);

    void validateManager(Object target, Errors errors);
}
