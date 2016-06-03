package ua.softserveinc.tc.constants;



/**
 * Created by Nestor on 13.05.2016.
 * Interface stores all validation-related constants
 */
public interface ValidationConst {
    String PASSWORD_REGEX = "^(\\S){8,24}$";
    String PHONE_NUMBER_REGEX = "^(\\+38|8|)\\W*\\d{10}\\W*$";
    String EMAIL_REGEX = "^(\\w){1,60}[@][s]oft[s]erveinc[.]com$";

    String FIRST_NAME = "firstName";
    String LAST_NAME = "lastName";
    String CHILD_DATE_OF_BIRTH = "dateOfBirth";

    String EMAIL = "email";
    String PASSWORD = "password";
    String PHONE_NUMBER = "phoneNumber";
    String CONFIRM = "confirm";

    String EMPTY_FIELD_MSG = "registration.empty";
    String NAME_ERROR_MSG = "registration.kid.name";
    String DATE_ERROR_MSG = "registration.kid.date";
    String PASSWORD_ERROR_MSG = "registration.password";
    String PHONE_NUMBER_ERROR_MSG  = "registration.phone";
    String NOT_CONFIRMED_MSG = "registration.confirm";
    String EMAIL_ERROR_MSG = "registration.email";
    String EMAIL_ALREADY_IN_USE_MSG = "registration.emailExist";
    String USER_NOT_EXIST = "user.notExist";
}
