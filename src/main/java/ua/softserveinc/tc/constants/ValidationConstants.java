package ua.softserveinc.tc.constants;

/**
 * Created by Nestor on 13.05.2016.
 * Interface stores all validation-related constants
 */
public final class ValidationConstants {
    public static final int EVENT_DESCRIPTION_MAX_LENGHT = 250;
    public static final int ONE_MINUTE = 1;

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";
    public static final String ONLY_DATE_FORMAT = "yyyy-MM-dd";
    public static final String PASSWORD_REGEX = "^(\\S){8,24}$";
    public static final String PHONE_NUMBER_REGEX = "^(\\+38|8|)\\W*\\d{10}\\W*$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String SIMPLY_PHONE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    public static final String LETTERS_REGEX = "^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦц" +
            "ЧчШшЩщЬьЮюЯя]*$";

    public static final String LETTERS_NUMBERS_SPACES_REGEX = "^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОо" +
            "ПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя0-9\\s]*$";

    public static final String SIMPLE_EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String DATE_REGEX = "^(\\d{4})(\\/|-)(\\d{1,2})(\\/|-)(\\d{1,2})$";

    public static final String DATE_IS_NOT_VALID = "Date is not valid";

    public static final String NO_SPACES_MESSAGE = "Value is invalid. Can not enter spaces.";
    public static final String NOT_EMPTY_MESSAGE = "May not be empty.";
    public static final String NOT_VALID_MESSAGE = "Value is invalid.";
    public static final String DUPLICATE_BOOKING_MESSAGE = "Room is already booked for your kid at this time";
    public static final String ROOM_IS_FULL_MESSAGE = "This room is full at this time";
    public static final String ENDTIME_BEFORE_STARTTIME = "End time can't be before start time";

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String CHILD_DATE_OF_BIRTH = "dateOfBirth";
    public static final String COMMENT = "comment";


    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String USER_ID = "userId";
    public static final String KID_ID = "kidId";
    public static final String ROOM_ID = "roomId";
    public static final String EVENT_TITLE = "name";
    public static final String EVENT_COLOR = "color";
    public static final String EVENT_DESCRIPTION = "description";


    public static final String EMAIL = "email";
    public static final String IMAGE = "file";
    public static final String PASSWORD = "password";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String CONFIRM = "confirm";

    public static final String TIME_IS_NOT_VALID = "administrator.timeStartEnd";
    public static final String TIME_FIELD = "timeStartEnd";
    public static final String DATE_FIELD = "date";

    public static final String EMPTY_NAME_MSG = "registration.emptyName";
    public static final String EMPTY_SURNAME_MSG = "registration.emptySurname";
    public static final String EMPTY_FIELD_MSG = "registration.empty";
    public static final String EVENT_DATE_FORMAT_INVALID_MSG = "Can't convert data type";
    public static final String EVENT_PAST_TIME_CREATION_MSG = "Must create new event in Present time";
    public static final String EVENT_DESCRIPTION_LENGTH_ERROR_MSG = "Description  should be less than 250 characters";
    public static final String EVENT_INACTIVE_ROOM_ERROR_MSG = "You can't create event on inactive room. Please contact to admin";
    public static final String EVENT_END_MUST_BIGGER_ONE_MINUTE_MSG = "End time must be bigger than start at least one minute";
    public static final String EVENT_RECCURRENT_END_MUST_BIGER_ONE_DAY_MSG = "End date must be bigger than start at least one day";
    public static final String EMAIL_NOT_VALID = "registration.manager.emailNotValid";
    public static final String NAME_ERROR_MSG = "registration.kid.name";
    public static final String NAME_NOT_EDITTED = "registration.kid.nameNotEdited";
    public static final String DATE_ERROR_MSG = "registration.kid.date";
    public static final String LASTNAME_ERROR_MSG = "registration.kid.lastName";
    public static final String FIRSTNAME_ERROR_MSG = "registration.kid.firstName";
    public static final String COMMENT_ERROR_MSG = "registration.kid.comment";
    public static final String IMAGE_VALIDATION_NOT_CORRECT_USAGE = "image.validator.error";
    public static final String IMAGE_VALIDATION_EMPTY_FILE = "image.validator.emptyFile";
    public static final String IMAGE_VALIDATION_NOT_ACCEPTABLE_SIZE = "image.validator.TooBigSize";
    public static final String IMAGE_VALIDATION_NOT_ACCEPTABLE_FORMAT = "image.validator.badFormat";

    public static final String EVENT_EMPTY_TITLE_MSG = "event.emptyTitle";
    public static final String EVENT_START_NOT_EQUALS_END_MSG = "event.startDateNotEqualEnd";
    public static final String EVENT_START_TIME_BIGGER_END_MSG = "event.startTimeBiggerEnd";

    public static final String PASSWORD_ERROR_MSG = "registration.password";
    public static final String PHONE_NUMBER_ERROR_MSG = "registration.phone";
    public static final String NOT_CONFIRMED_MSG = "registration.confirm";
    public static final String EMAIL_ERROR_MSG = "registration.email";
    public static final String EMAIL_ALREADY_IN_USE_MSG = "registration.emailExist";
    public static final String USER_NOT_EXIST = "user.notExist";
    public static final String ROOM_NOT_EXIST = "room.notExist";
    public static final String CHILD_NOT_EXIST = "child.notExist";
    public static final String PROPERTIES_WRITE_FAILED = "properties.update.failed";
    public static final String FAILED_SEND_EMAIL_MSG = "email.failed";
    public static final String FILE_TOO_BIG = "kid.image.tooBig";
    public static final String FILE_WRONG_EXTENSION = "kid.image.ext";
    //public static final String ADD_ROOM_DIALOG_RATE_ERROR = "administrator.room.error.rate";
    public static final String ADD_ROOM_DIALOG_RATE_ERROR = "email.failed";
    public static final String NO_DAYS_FOR_BOOKING = "There are no days for booking";


    private ValidationConstants() {
    }

    public static final class ConfigFields {
        public static final String MIN_AGE = "kidsMinAge";
        public static final String MAX_AGE = "kidsMaxAge";
        public static final String MAX_NAME_LENGTH = "kidsMaxNameLength";
        public static final String MAX_COMMENT_LENGTH = "kidsMaxCommentLength";


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
        public static final String SUCCESSFUL_CONFIRMATION_MESSAGE = "Registration confirmed!";

        private ConfigFields() {
        }
    }
}
