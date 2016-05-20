package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.ValidationConst;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.util.regex.Pattern;

/**
 * Created by Nestor on 13.05.2016.
 */

@Component
public class UserValidator implements Validator{

    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);

    }

    @Override
    public void validate(Object o,  Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",  "registration.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",  "registration.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",  "registration.empty");

        if(!Pattern.compile(ValidationConst.PASSWORD_REGEX)
                .matcher(user.getPassword())
                .matches()){
            errors.rejectValue("password", "registration.password");
        }

        if(!Pattern.compile(ValidationConst.PHONE_NUMBER_REGEX)
                    .matcher(user.getPhoneNumber())
                    .matches()){
                errors.rejectValue("phoneNumber", "registration.phone");
        }

        if(!Pattern.compile(ValidationConst.EMAIL_REGEX)
                .matcher(user.getEmail())
                .matches()){
            errors.rejectValue("email", "registration.email");
        }

        if(!user.getPassword().equals(user.getConfirm())){
            errors.rejectValue("confirm", "registration.confirm");
        }

        if(userService.getUserByEmail(user.getEmail())!=null){
            errors.rejectValue("email", "registration.emailExist");
        }
    }

}
