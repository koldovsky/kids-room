package ua.softserveinc.tc.validator;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.RoomDto;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.JsonUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component("roomValidator")
public class RoomValidatorImpl implements RoomValidator {

    @Autowired
    private RoomService roomService;

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

            if (!Pattern.compile(ValidationConstants.NAME_REGEX)
                    .matcher(roomToValidate.getName())
                    .matches()) {
                errors.rejectValue(ValidationConstants.ROOM_NAME, ValidationConstants.ROOM_INVALID_NAME_MSG);
            }
            if (!Pattern.compile(ValidationConstants.LETTERS_NUMBERS_SPACES_REGEX)
                    .matcher(roomToValidate.getAddress())
                    .matches()) {
                errors.rejectValue(ValidationConstants.ROOM_ADDRESS, ValidationConstants.ROOM_INVALID_ADDRESS_MSG);
            }
            if (!Pattern.compile(ValidationConstants.NAME_REGEX)
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
            int roomCapacity = (roomToValidate.getCapacity() == null) ? 0 : roomToValidate.getCapacity();
            if ((roomCapacity < ValidationConstants.ROOM_CAPACITY_MINIMUM) || (roomCapacity > ValidationConstants.ROOM_CAPACITY_MAXIMUM)) {
                errors.rejectValue(ValidationConstants.ROOM_CAPACITY, ValidationConstants.ROOM_MIN_MAX_CAPACITY);
            }
            String jsonManagers = roomToValidate.getManagers();
            if (Pattern.compile(ValidationConstants.MANAGER_ID_REGEX)
                    .matcher(jsonManagers)
                    .find()) {
                errors.rejectValue(ValidationConstants.MANAGERS_FIELD, ValidationConstants.ROOM_MANAGER_INVALID);
            } else {
                List<UserDto> managers = Arrays.asList(new Gson().fromJson(jsonManagers, UserDto[].class));
                List<Long> managerId = new ArrayList<>();
                for (UserDto id : managers) {
                    managerId.add(id.getId());
                }
                if (managers.stream().anyMatch(manager -> (manager.getId() == null))) {
                    errors.rejectValue(ValidationConstants.MANAGERS_FIELD, ValidationConstants.ROOM_MANAGER_EMPTY);
                }
                if (managerId.stream().distinct().count() != managerId.size()) {
                    errors.rejectValue(ValidationConstants.MANAGERS_FIELD, ValidationConstants.ROOM_MANAGER_DUPLICATE);
                }

            }
            String jsonRate = roomToValidate.getRate();
            List<Rate> rates = JsonUtil.fromJsonList(jsonRate, Rate[].class);
            if (rates.stream().anyMatch(rate -> ((rate.getHourRate() == null) || (rate.getPriceRate() == null)
                    || (rate.getHourRate() > 24) || (rate.getHourRate() < 1) || (rate.getPriceRate() < 0)))) {
                errors.rejectValue(ValidationConstants.ROOM_RATE_FIELD, ValidationConstants.ROOM_RATE_ERROR);

            }

            if ((Pattern.compile(ValidationConstants.TWENTY_FOUR_HOURS_REGEX)
                    .matcher(roomToValidate.getWorkingHoursStart()).matches())
                    && Pattern.compile(ValidationConstants.TWENTY_FOUR_HOURS_REGEX)
                    .matcher(roomToValidate.getWorkingHoursEnd()).matches()) {

                LocalTime startTime = LocalTime.parse(roomToValidate.getWorkingHoursStart());
                LocalTime endTime = LocalTime.parse(roomToValidate.getWorkingHoursEnd());
                if (startTime.isAfter(endTime)) {
                    errors.rejectValue(ValidationConstants.TIME_FIELD, ValidationConstants.TIME_IS_NOT_VALID);
                }
            } else {
                errors.rejectValue(ValidationConstants.TIME_FIELD, ValidationConstants.ROOM_WRONG_TIME_FORMAT);
            }

        } else {
            errors.rejectValue(ValidationConstants.ROOM_NAME, ValidationConstants.ROOM_WRONG_CAST_MSG);
        }
    }

    public List<String> checkRoomBookings(Room room) {
        List<String> warnings = new ArrayList<>();
        if (roomService.hasActiveBooking(room)) {
            warnings.add(ValidationConstants.ROOM_HAS_ACTIVE_BOOKINGS);
        }
        if (roomService.hasPlanningBooking(room)) {
            warnings.add(ValidationConstants.ROOM_HAS_PLANNING_BOOKING);
        }

        return warnings;
    }
}
