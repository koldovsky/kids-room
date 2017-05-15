package ua.softserveinc.tc.constants;

/**
 * Interface stores all validation-related constants
 */
public final class ValidationConstants {
    public static final int EVENT_DESCRIPTION_MAX_LENGHT = 250;
    public static final int EVENT_TITLE_MAX_LENGHT = 255;
    public static final int KID_COMMENT_MAX_LENGHT = 250;
    public static final int ONE_MINUTE = 1;
    public static final int MAX_NAME_CHARACTER = 35;
    public static final int ROOM_FIELDS_MINIMUM_CHARACTER = 2;
    public static final int ROOM_FIELDS_MAXIMUM_CHARACTER = 255;
    public static final int ROOM_CAPACITY_MINIMUM = 1;
    public static final int ROOM_CAPACITY_MAXIMUM = 200;
    public static final int ABONNEMENT_NAME_MIN_CHARACTERS = 2;
    public static final int ABONNEMENT_NAME_MAX_CHARACTERS = 32;
    public static final int ABONNEMENT_PRICE_MIN_VALUE = 0;
    public static final int ABONNEMENT_HOUR_MIN_VALUE = 0;
    public static final int ABONNEMENT_HOUR_MAX_VALUE = 10000;

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";
    public static final String ONLY_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_DOESNT_EXIST = "date.not.exist";
    public static final String PASSWORD_REGEX = "^(\\S){8,24}$";
    public static final String PHONE_NUMBER_REGEX = "^(\\+38|8|)\\W*\\d{10}\\W*$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String DISCOUNT_VALUE_REGEX = "^[1-9][0-9]?$|^100$";
    public static final String DISCOUNT_REASON_REGEX = "^.{3,255}$";
    public static final String SIMPLY_PHONE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    public static final String NAME_REGEX = "^[a-zA-Zа-яА-ЯЇїІіЄєҐґ`´ʼ’'\\-\\s]+$";
    public static final String LETTERS_REGEX = "^[a-zA-Zа-щА-ЩЬьЮюЯяЇїІіЄєҐґ]*$";
    public static final String MANAGER_ID_REGEX = "\"id\":\"(\\D+)";
    public static final String ROOM_HOUR_RATE_REGEX = "\"hourRate\":\"(\\D+)";
    public static final String ROOM_PRICE_RATE_REGEX = "\"priceRate\":\"(\\^(?:[1-9]\\d*|0)?(?:\\.\\d+)?$)";
    public static final String TWENTY_FOUR_HOURS_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    public static final String NUMBERS_REGEX = "\\d+/g";

    public static final String LETTERS_NUMBERS_SPACES_REGEX = "^[a-zA-Zа-щА-ЩЬьЮюЯяЇїІіЄєҐґ0-9\\s]*$";

    public static final String SIMPLE_EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String DATE_REGEX = "^(\\d{4})(\\/|-)(\\d{1,2})(\\/|-)(\\d{1,2})$";

    public static final String ROOM_HAS_ACTIVE_BOOKINGS = "administrator.room.warning.active";
    public static final String ROOM_HAS_PLANNING_BOOKING = "administrator.room.warning.planned";

    public static final String DATE_IS_NOT_VALID = "Date is not valid";

    public static final String NO_SPACES_MESSAGE = "Value is invalid. Can not enter spaces.";
    public static final String NOT_EMPTY_MESSAGE = "Should not be empty.";
    public static final String NOT_VALID_MESSAGE = "Value is invalid.";
    public static final String ROOM_IS_FULL_MESSAGE = "This room is full at this time";

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String CHILD_DATE_OF_BIRTH = "dateOfBirth";
    public static final String COMMENT = "comment";


    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String USER_ID = "userId";
    public static final String KID_ID = "kidId";
    public static final String ROOM_ID = "roomId";
    public static final String DAYS_OF_WEEK = "daysOfWeek";
    public static final String EVENT_TITLE = "name";
    public static final String EVENT_COLOR = "color";
    public static final String EVENT_DESCRIPTION = "description";
    public static final String MONTH_RECURRENT_DAYS = "daysOfTheMonth";
    public static final String WEEK_RECURRENT_DAYS = "daysOfWeek";

    public static final String RECURRENT_BOOKING = "idBook";
    public static final String COMMON_ERROR_MESSAGE = "image.validator.error";
    public static final String END_TIME_BEFORE_START_TIME = "timeValidation.endTimeBeforeStart";
    public static final String BAD_TIME_FORMAT = "timeValidation.badTimeFormat";
    public static final String OUT_OF_WORKING_HOURS = "timeValidation.outOfWorkingHours";
    public static final String DUPLICATE_BOOKING_MESSAGE = "duplicate.booking";
    public static final String DUPLICATE_BOOKINGS_MESSAGE = "Sorry, room is already booked for your kid at this time";
    public static final String NO_DAYS_FOR_BOOKING = "no.day.forBook";

    public static final String ROOM_NAME = "name";
    public static final String ROOM_ADDRESS = "address";
    public static final String ROOM_CITY = "city";
    public static final String ROOM_PHONE_NUMBER = "phoneNumber";
    public static final String ROOM_CAPACITY = "capacity";
    public static final String ROOM_WORKING_HOURS_START = "workingHoursStart";
    public static final String ROOM_WORKING_HOURS_END = "workingHoursEnd";
    public static final String ROOM_RATE_FIELD = "rate";

    public static final String EMAIL = "email";
    public static final String IMAGE = "file";
    public static final String PASSWORD = "password";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String CONFIRM = "confirm";

    public static final String TIME_IS_NOT_VALID = "administrator.timeStartEnd";
    public static final String TIME_FIELD = "timeStartEnd";
    public static final String MANAGERS_FIELD = "managers";
    public static final String DATE_FIELD = "date";

    public static final String ABONNEMENT_NAME = "name";
    public static final String ABONNEMENT_HOUR = "hour";
    public static final String ABONNEMENT_PRICE = "price";
    public static final String ABONNEMENT_ACTIVE = "isActive";

    public static final String DAY_DISCOUNT_REASON = "reason";
    public static final String DAY_DISCOUNT_VALUE = "value";
    public static final String DAY_DISCOUNT_START_DATE = "startDate";
    public static final String DAY_DISCOUNT_END_DATE = "endDate";
    public static final String DAY_DISCOUNT_START_TIME = "startTime";
    public static final String DAY_DISCOUNT_END_TIME = "endTime";
    public static final String PER_DISCOUNT_USER = "user";
    public static final String INCORRECT_USER = "incorrect";

    public static final String EMPTY_DAY_DISCOUNT_REASON = "administrator.discount.error.emptyReason";
    public static final String EMPTY_DAY_DISCOUNT_VALUE = "administrator.discount.error.emptyValue";
    public static final String EMPTY_DAY_DISCOUNT_START_DATE = "administrator.discount.error.emptyStartDate";
    public static final String EMPTY_DAY_DISCOUNT_END_DATE = "administrator.discount.error.emptyEndDate";
    public static final String EMPTY_DAY_DISCOUNT_START_TIME = "administrator.discount.error.emptyStartTime";
    public static final String EMPTY_DAY_DISCOUNT_END_TIME = "administrator.discount.error.emptyEndTime";
    public static final String EMPTY_PER_DISCOUNT_USER = "administrator.discount.error.noUser";
    public static final String END_DATE_IS_BEFORE = "administrator.discount.error.endDateBefore";
    public static final String END_TIME_IS_BEFORE = "administrator.discount.error.endTimeBefore";
    public static final String DAY_DISCOUNT_WRONG_REASON = "administrator.discount.error.wrongReason";
    public static final String DAY_DISCOUNT_WRONG_VALUE = "administrator.discount.error.wrongValue";
    public static final String DAY_DISCOUNT_ALREADY_HAVE = "administrator.discount.error.alreadyHave";

    public static final String ROOM_EPMTY_MSG = "administrator.room.error.emptyField";
    public static final String ROOM_INVALID_NAME_MSG = "administrator.room.error.invalidName";
    public static final String ROOM_INVALID_ADDRESS_MSG = "administrator.room.error.invalidAddress";
    public static final String ROOM_INVALID_CITY_MSG = "administrator.room.error.invalidCity";
    public static final String ROOM_INVALID_PHONE_MSG = "administrator.room.error.invalidPhone";
    public static final String ROOM_MIN_MAX_CHARACTERS_MSG = "administrator.room.error.invalidMinMaxCharacter";
    public static final String ROOM_WRONG_CAST_MSG = "administrator.room.error.cast";
    public static final String ROOM_WRONG_TIME_FORMAT = "administrator.room.error.timeCast";
    public static final String ROOM_MIN_MAX_CAPACITY = "administrator.room.error.capacity";
    public static final String ROOM_MANAGER_INVALID = "administrator.room.error.managerInvalid";
    public static final String ROOM_MANAGER_EMPTY = "administrator.room.error.managerEmpty";
    public static final String ROOM_MANAGER_DUPLICATE = "administrator.room.error.managerDuplicate";
    public static final String ROOM_RATE_ERROR = "administrator.room.error.rate";
    public static final String ROOM_MANAGER_INCORRECT ="administrator.room.error.managerIncorrect";
    public static final String ROOM_RATE_INCORRECT ="administrator.room.error.rateIncorrect";
    public static final String ROOM_RATE_DUPLICATE ="administrator.room.error.rateDuplicate";

    public static final String EMPTY_NAME_MSG = "registration.emptyName";
    public static final String EMPTY_SURNAME_MSG = "registration.emptySurname";
    public static final String EMPTY_FIELD_MSG = "registration.empty";
    public static final String EVENT_DATE_FORMAT_INVALID_MSG = "Can't convert data type";
    public static final String EVENT_CAST_EXCEPTION = "Your date is wrong";
    public static final String EVENT_PAST_TIME_CREATION_MSG = "event.pastCreation";
    public static final String EVENT_INACTIVE_ROOM_ERROR_MSG = "event.innactiveRoom";
    public static final String EVENT_END_MUST_BIGGER_ONE_MINUTE_MSG = "event.startTimeBiggerEndTime";
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
    public static final String IMAGE_VALIDATION_CORRUPTION_FILE = "image.validator.corruptedFile";
    public static final String IMAGE_VALIDATION_NOT_ACCEPTABLE_SIZE = "image.validator.TooBigSize";
    public static final String IMAGE_VALIDATION_NOT_ACCEPTABLE_FORMAT = "image.validator.badFormat";
    public static final String IMAGE_VALIDATION_NOT_ACCEPTABLE_SIZE_FORMAT = "image.validator.badSizeFormat";

    public static final String EVENT_EMPTY_TITLE_MSG = "event.emptyTitle";
    public static final String EVENT_TITLE_ERROR_MSG = "event.errorTitle";
    public static final String EVENT_MAX_TITLE_LENGHT = "event.maxTitleLenght";
    public static final String EVENT_INVALID_COLOR = "event.invalidColor";
    public static final String EVENT_DATE_ERROR_PARSING = "event.dateErrorParsing";
    public static final String EVENT_NO_DAY_SELECTED = "event.noDaySelected";
    public static final String EVENT_INVALID_DAY_SELECTED = "event.invalidDaySelected";

    public static final String EVENT_DESCRIPTION_LENGTH_ERROR_MSG = "event.descriptionLenght";
    public static final String EVENT_START_NOT_EQUALS_END_MSG = "event.startDateNotEqualEnd";
    public static final String EVENT_START_TIME_BIGGER_END_MSG = "event.startTimeBiggerEndTime";
    public static final String EVENT_RECURRENT_START_NOT_BEFORE_END = "event.recurrentEndBeforeStart";

    public static final String PASSWORD_ERROR_MSG = "registration.password";
    public static final String PHONE_NUMBER_ERROR_MSG = "registration.phone";
    public static final String NOT_CONFIRMED_MSG = "registration.confirm";
    public static final String EMAIL_ERROR_MSG = "registration.email";
    public static final String EMAIL_ALREADY_IN_USE_MSG = "User with such email have already exist";
    public static final String USER_NOT_EXIST = "user.notExist";
    public static final String ROOM_NOT_EXIST = "room.notExist";
    public static final String CHILD_NOT_EXIST = "child.notExist";
    public static final String PROPERTIES_WRITE_FAILED = "properties.update.failed";
    public static final String PROBLEM_SEND_EMAIL_MSG = "email.problem";
    public static final String PROBLEM_EMPTY_USER = "user.problem";
    public static final String FAILED_SEND_EMAIL_MSG = "Error with sending email. Please try again to register";
    public static final String NOT_SEND_EMAIL_MSG = "=Error! Email didn't sent";
    public static final String FILE_TOO_BIG = "kid.image.tooBig";
    public static final String FILE_WRONG_EXTENSION = "kid.image.ext";
    public static final String ADD_ROOM_DIALOG_RATE_ERROR = "email.failed";
    public static final String NO_DAYS_FOR_RECURRENT_EVENT = "recurrent.daysNotSellected";
    public static final String EXIST = "report.exist";
    public static final String MANAGER_EMPTY = "Empty manager";

    public static final String ABONNEMENT_EMPTY_FIELD = "This field can't be empty";
    public static final String ABONNEMENT_INVALID_NAME_MSG = "administrator.abonnement.error.name";
    public static final String ABONNEMENT_INVALID_PRICE_MSG = "administrator.abonnement.error.price";
    public static final String ABONNEMENT_NAME_LENGHT = "administrator.abonnement.error.nameLength";
    public static final String ABONNEMENT_PRICE_SIZE = "administrator.abonnement.error.priceSize";
    public static final String ABONNEMENT_HOUR_LENGTH = "administrator.abonnement.error.hourLength";

    public static final String ADMINISTRATOR_INCORRECT_FIRST_NAME = "administrator.addManager.name";
    public static final String ADMINISTRATOR_INCORRECT_SECOND_NAME = "administrator.addManager.lastName";
    public static final String ADMINISTRATOR_INCORRECT_PHONE = "administrator.addManager.phone";


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
        public static final String SUCCESSFUL_CONFIRMATION_MESSAGE = "Registration confirmed!";

        private ConfigFields() {
        }
    }
}
