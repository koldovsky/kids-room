package ua.softserveinc.tc.constants;

/**
 * Created by Nestor on 13.05.2016.
 * Interface stores all validation-related constants
 */
public final class ValidationConstants {
    public static final String PASSWORD_REGEX = "^(\\S){8,24}$";
    public static final String PHONE_NUMBER_REGEX = "^(\\+38|8|)\\W*\\d{10}\\W*$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    // public static final String EMAIL_REGEX = "^(\\w){1,60}[@][s]oft[s]erveinc[.]com$";
    public static final String SIMPLY_PHONE_REGEX = "^([0-9]{5,14})*$";
    public static final String LETTERS_REGEX = "^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦц" +
            "ЧчШшЩщЬьЮюЯя]*$";
    public static final String LETTERS_NUMBERS_SPACES_REGEX = "^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОо" +
            "ПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя0-9\\s]*$";
    public static final String SIMPLE_EMAIL_REGEX = "^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@(([0-9]{1,3})|([a-zA-Z]{2,11})" +
            "|(aero|coop|info|museum|name))+(\\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name)))*" +
            "\\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name))*$";

    public static final String NO_SPACES_MESSAGE = "Value is invalid. Can not enter spaces.";
    public static final String NOT_EMPTY_MESSAGE = "May not be empty.";
    public static final String NOT_VALID_MESSAGE = "Value is invalid.";

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String CHILD_DATE_OF_BIRTH = "dateOfBirth";

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String CONFIRM = "confirm";

    public static final String TIME_IS_NOT_VALID = "administrator.timeStartEnd";
    public static final String TIME_FIELD = "timeStartEnd";

    public static final String EMPTY_NAME_MSG = "registration.emptyName";
    public static final String EMPTY_SURNAME_MSG = "registration.emptySurname";
    public static final String EMPTY_FIELD_MSG = "registration.empty";

    public static final String EMAIL_NOT_VALID = "registration.manager.emailNotValid";
    public static final String NAME_ERROR_MSG = "registration.kid.name";
    public static final String DATE_ERROR_MSG = "registration.kid.date";
    public static final String PASSWORD_ERROR_MSG = "registration.password";
    public static final String PHONE_NUMBER_ERROR_MSG = "registration.phone";
    public static final String NOT_CONFIRMED_MSG = "registration.confirm";
    public static final String EMAIL_ERROR_MSG = "registration.email";
    public static final String EMAIL_ALREADY_IN_USE_MSG = "registration.emailExist";
    public static final String USER_NOT_EXIST = "user.notExist";
    public static final String PROPERTIES_WRITE_FAILED = "properties.update.failed";
    public static final String FAILED_SEND_EMAIL_MSG = "email.failed";
    public static final String FILE_TOO_BIG = "kid.image.tooBig";
    public static final String FILE_WRONG_EXTENSION = "kid.image.ext";

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

        public static final String CLEAN_UP_DAY = "daysToCleanUpBookings";
        public static final String CLEAN_UP_HOUR = "hourToCleanUpBookings";
        public static final String CLEAN_UP_MINUTE = "minutesToCleanUpBookings";

        public static final String REMINDER_HOUR = "hourToSendEmailReminder";
        public static final String REMINDER_MINUTE = "minutesToSendEmailReminder";

        public static final String ERROR_MSG_PLACEHOLDER = "errorMsg";

        public static final String NOT_VALID_DATE_MSG = "date.not.valid";
        public static final String NOT_VALID_TIME_MSG = "time.not.valid";
        public static final String NOT_VALID_VALUE = "value.not.valid";
        public static final String VALUES_INAPPROPRIATE = "values.inappropriate";

        public static final String MAX_UPLOAD_IMG_SIZE = "maxUploadImgSizeMb";

        private ConfigFields() {
        }
    }
}
