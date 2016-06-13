package ua.softserveinc.tc.constants;

/**
 * Created by Nestor on 13.05.2016.
 * Interface stores all validation-related constants
 */
public final class ValidationConstants {
    public static final String PASSWORD_REGEX = "^(\\S){8,24}$";
    public static final String PHONE_NUMBER_REGEX = "^(\\+38|8|)\\W*\\d{10}\\W*$";
    public static final String EMAIL_REGEX = "^(\\w){1,60}[@][s]oft[s]erveinc[.]com$";

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String CHILD_DATE_OF_BIRTH = "dateOfBirth";

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String CONFIRM = "confirm";

    public static final String EMPTY_FIELD_MSG = "registration.empty";
    public static final String NAME_ERROR_MSG = "registration.kid.name";
    public static final String DATE_ERROR_MSG = "registration.kid.date";
    public static final String PASSWORD_ERROR_MSG = "registration.password";
    public static final String PHONE_NUMBER_ERROR_MSG = "registration.phone";
    public static final String NOT_CONFIRMED_MSG = "registration.confirm";
    public static final String EMAIL_ERROR_MSG = "registration.email";
    public static final String EMAIL_ALREADY_IN_USE_MSG = "registration.emailExist";
    public static final String USER_NOT_EXIST = "user.notExist";

    private ValidationConstants() {
    }

    public static final class ConfigFields {
        public static final String MIN_AGE = "kidsMinAge";
        public static final String MAX_AGE = "kidsMaxAge";

        public static final String CALCULATION_HOUR = "hourToCalculateBookingsEveryDay";
        public static final String CALCULATION_MINUTE = "minutesToCalculateBookingsEveryDay";
        public static final String EMAIL_REPORT_DAY = "dayToSendEmailReport";
        public static final String EMAIL_REPORT_HOUR = "hourToSendEmailReport";
        public static final String EMAIL_REPORT_MINUTE = "minutesToSendEmailReport";
        public static final String MIN_PERIOD = "minPeriodSize";
        public static final String SERVER_NAME = "serverName";

        public static final String NOT_VALID_DATE_MSG = "date.not.valid";
        public static final String NOT_VALID_TIME_MSG = "time.not.valid";

        private ConfigFields(){}
    }
}
