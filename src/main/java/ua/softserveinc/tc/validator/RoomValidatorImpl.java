package ua.softserveinc.tc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.RoomDto;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

@Component
public class RoomValidatorImpl implements RoomValidator {

    /**
     * This Validator validates just RoomDto instances
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return RoomDto.class.equals(aClass);
    }

    /**
     * Method contains validation logic
     *
     * @param o      Object to be validated
     * @param errors An object to store validation errors
     */
    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof RoomDto) {
            RoomDto roomToValidate = (RoomDto) o;

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.ROOM_NAME, ValidationConstants.ROOM_EPMTY_MSG);
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.ROOM_ADDRESS, ValidationConstants.ROOM_EPMTY_MSG);
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.ROOM_CITY, ValidationConstants.ROOM_EPMTY_MSG);
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.ROOM_PHONE_NUMBER, ValidationConstants.ROOM_EPMTY_MSG);
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.ROOM_CAPACITY, ValidationConstants.ROOM_EPMTY_MSG);
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.ROOM_WORKING_HOURS_START, ValidationConstants.ROOM_EPMTY_MSG);
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.ROOM_WORKING_HOURS_END, ValidationConstants.ROOM_EPMTY_MSG);

            if (!Pattern.compile(ValidationConstants.LETTERS_REGEX)
                    .matcher(roomToValidate.getName())
                    .matches()) {
                errors.rejectValue(ValidationConstants.ROOM_NAME, ValidationConstants.ROOM_INVALID_NAME_MSG);
            }
            if (!Pattern.compile(ValidationConstants.LETTERS_NUMBERS_SPACES_REGEX)
                    .matcher(roomToValidate.getAddress())
                    .matches()) {
                errors.rejectValue(ValidationConstants.ROOM_ADDRESS, ValidationConstants.ROOM_INVALID_ADDRESS_MSG);
            }
            if (!Pattern.compile(ValidationConstants.LETTERS_REGEX)
                    .matcher(roomToValidate.getCity())
                    .matches()) {
                errors.rejectValue(ValidationConstants.ROOM_CITY, ValidationConstants.ROOM_INVALID_CITY_MSG);
            }
            if (!Pattern.compile(ValidationConstants.SIMPLY_PHONE_REGEX)
                    .matcher(roomToValidate.getPhoneNumber())
                    .matches()) {
                errors.rejectValue(ValidationConstants.ROOM_PHONE_NUMBER, ValidationConstants.ROOM_INVALID_PHONE_MSG);
            }

            int roomNameLength = roomToValidate.getName().length();
            if ((roomNameLength < ValidationConstants.ROOM_FIELDS_MINIMUM_CHARACTER) || (roomNameLength > ValidationConstants.ROOM_FIELDS_MAXIMUM_CHARACTER)) {
                errors.rejectValue(ValidationConstants.ROOM_NAME, ValidationConstants.ROOM_MIN_MAX_CHARACTERS_MSG);
            }
            int roomAddressLength = roomToValidate.getAddress().length();
            if ((roomAddressLength < ValidationConstants.ROOM_FIELDS_MINIMUM_CHARACTER) || (roomAddressLength > ValidationConstants.ROOM_FIELDS_MAXIMUM_CHARACTER)) {
                errors.rejectValue(ValidationConstants.ROOM_ADDRESS, ValidationConstants.ROOM_MIN_MAX_CHARACTERS_MSG);
            }
            int roomCityLength = roomToValidate.getCity().length();
            if ((roomCityLength < ValidationConstants.ROOM_FIELDS_MINIMUM_CHARACTER) || (roomCityLength > ValidationConstants.ROOM_FIELDS_MAXIMUM_CHARACTER)) {
                errors.rejectValue(ValidationConstants.ROOM_CITY, ValidationConstants.ROOM_MIN_MAX_CHARACTERS_MSG);
            }
            int roomPhoneLength = roomToValidate.getPhoneNumber().length();
            if ((roomPhoneLength < ValidationConstants.ROOM_FIELDS_MINIMUM_CHARACTER) || (roomPhoneLength > ValidationConstants.ROOM_FIELDS_MAXIMUM_CHARACTER)) {
                errors.rejectValue(ValidationConstants.ROOM_PHONE_NUMBER, ValidationConstants.ROOM_MIN_MAX_CHARACTERS_MSG);
            }
            int roomCapacity = (roomToValidate.getCapacity() == null) ? 0 : roomToValidate.getCapacity();
            if ((roomCapacity < ValidationConstants.ROOM_CAPACITY_MINIMUM) || (roomCapacity > ValidationConstants.ROOM_CAPACITY_MAXIMUM)) {
                errors.rejectValue(ValidationConstants.ROOM_CAPACITY, ValidationConstants.ROOM_MIN_MAX_CAPACITY);
            }

            try {
                LocalTime startTime = LocalTime.parse(roomToValidate.getWorkingHoursStart());
                LocalTime endTime = LocalTime.parse(roomToValidate.getWorkingHoursEnd());
                if (startTime.isAfter(endTime)) {
                    errors.rejectValue(ValidationConstants.TIME_FIELD, ValidationConstants.TIME_IS_NOT_VALID);
                }
            } catch (DateTimeParseException ex) {
                errors.rejectValue(ValidationConstants.TIME_FIELD, ValidationConstants.ROOM_WRONG_TIME_FORMAT);
            }

        } else {
            errors.rejectValue(ValidationConstants.ROOM_NAME, ValidationConstants.ROOM_WRONG_CAST_MSG);
        }
    }
}
