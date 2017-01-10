package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.service.RoomService;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

import ua.softserveinc.tc.entity.Room;

/*
 * Created by Sviatoslav Hryb on 09-Jan-17.
 */
@Component
public class InputDateValidatorImpl implements InputDateValidator {

    @Autowired
    private RoomService roomService;

    private final List<String> errors = new ArrayList<>();

    @Override
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    @Override
    public boolean validate(List<BookingDto> listDto) {
        boolean result = true;
        errors.clear();

        if(listDto == null || listDto.isEmpty() || listDto.get(0).getStartTime() == null
                || listDto.get(0).getEndTime() == null) {
            errors.add(ValidationConstants.VALIDATION_NOT_CORRECT_USAGE);

            result = false;
        } else if (!hasCorrectTimeFormat(listDto)) {
            errors.add(ValidationConstants.BAD_TIME_FORMAT);

            result = false;
        } else if (!hasCorrectTimeRelations(listDto)) {
            errors.add(ValidationConstants.END_TIME_BEFORE_START_TIME);

            result = false;
        } else if (!areDatesInTheWorkingHoursRange(listDto)) {
            errors.add(ValidationConstants.OUT_OF_WORKING_HOURS);

            result = false;
        }

        return result;
    }

    /*
     * Checks if the start and the end dates from the BookingDto have correct format
     *
     * @param dtoList the given list of BookingDto
     * @return true if the given string represents a correct time format,
     * otherwise - false.
     */
    private boolean hasCorrectTimeFormat(List<BookingDto> dtoList) {
        BookingDto singleDto = dtoList.get(0);

        return Pattern.matches(DateConstants.DATE_T_TIME_REGEXP, singleDto.getStartTime())
                && Pattern.matches(DateConstants.DATE_T_TIME_REGEXP, singleDto.getEndTime());
    }

    /*
     * Checks if the start and the end dates from the BookingDto have correct time relations
     *
     * @param dtoList the given list of BookingDto
     * @return true if the given string represents a correct time relations,
     * otherwise - false.
     */
    private boolean hasCorrectTimeRelations(List<BookingDto> dtoList) {
        BookingDto singleDto = dtoList.get(0);
        boolean result = true;
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.parse(singleDto.getStartTime());
        LocalDateTime endTime = LocalDateTime.parse(singleDto.getEndTime());

        if (!startTime.isBefore(endTime) || startTime.isBefore(currentDate)) {
            result = false;
        }

        return result;
    }

    /*
     * Checks if the start and the end dates from the BookingDto are in the range of working
     * hours.
     *
     * @param dtoList the given list of BookingDto
     * @return true if the given string represents a correct time from range,
     * otherwise - false.
     */
    private boolean areDatesInTheWorkingHoursRange(List<BookingDto> dtoList) {
        boolean result = false;
        BookingDto singleDto = dtoList.get(0);
        Room room = (singleDto.getRoom() == null) ?
                roomService.findEntityById(singleDto.getRoomId()) : singleDto.getRoom();

        if(room != null) {
            singleDto.setRoom(room);
            LocalTime workingHoursStart = LocalTime.parse(room.getWorkingHoursStart());
            LocalTime workingHoursEnd = LocalTime.parse(room.getWorkingHoursEnd());
            LocalTime dtoStartTime = LocalDateTime.parse(singleDto.getStartTime()).toLocalTime();
            LocalTime dtoEndTime = LocalDateTime.parse(singleDto.getEndTime()).toLocalTime();
            result = !workingHoursStart.isAfter(dtoStartTime)
                    && !workingHoursEnd.isBefore(dtoEndTime);
        }

        return result;
    }
}
