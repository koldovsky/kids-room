package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.MonthlyEventDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.service.RoomService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("eventValidator")
public class EventValidatorImpl implements EventValidator {

    @Autowired
    private RoomService roomService;

    @Override
    public List<String> generalEventValidation(EventDto eventDto) {
        List<String> generalErrorMessage = new ArrayList<>();
        if (!isEventCreateInActiveRoom(eventDto.getRoomId())) {
            generalErrorMessage.add(ValidationConstants.EVENT_INACTIVE_ROOM_ERROR_MSG);
        }
        return generalErrorMessage;
    }

    @Override
    public List<String> reccurentEventValidation(RecurrentEventDto recurrentEventDto) {
        List<String> reccurentEventValidationMessages = commonEventValidation(recurrentEventDto);
        if ((isTimeAndDateFormatValid(recurrentEventDto.getStartTime(), recurrentEventDto.getEndTime()))
                && (!isEndDateBiggerThanStartDate(recurrentEventDto.getStartTime(),
                recurrentEventDto.getEndTime()))) {
            reccurentEventValidationMessages.add(ValidationConstants.EVENT_RECCURRENT_END_MUST_BIGER_ONE_DAY_MSG);
        }
        if (recurrentEventDto.getDaysOfWeek() == null) {
            reccurentEventValidationMessages.add(ValidationConstants.EVENT_NO_DAY_SELECTED);
        } else if (!isReccurendDaysCorrected(recurrentEventDto)) {
            reccurentEventValidationMessages.add(ValidationConstants.EVENT_INVALID_DAY_SELECTED);
        }

        return reccurentEventValidationMessages;
    }

    @Override
    public List<String> singleEventValidation(EventDto eventDto) {
        List<String> singleEventValidationMessage = commonEventValidation(eventDto);
        if ((isTimeAndDateFormatValid(eventDto.getStartTime(), eventDto.getEndTime()))
                && (!isDateEquals(eventDto.getStartTime(), eventDto.getEndTime()))) {
            singleEventValidationMessage.add(ValidationConstants.EVENT_START_NOT_EQUALS_END_MSG);
        }
        return singleEventValidationMessage;
    }

    @Override
    public List<String> monthlyEventValidation(MonthlyEventDto monthlyEventDto) {
        List<String> montlyEventValidationMessages = commonEventValidation(monthlyEventDto);
        if ((isTimeAndDateFormatValid(monthlyEventDto.getStartTime(), monthlyEventDto.getEndTime()))
                && (!isEndDateBiggerThanStartDate(monthlyEventDto.getStartTime(),
                monthlyEventDto.getEndTime()))) {
            montlyEventValidationMessages.add(ValidationConstants.EVENT_RECCURRENT_END_MUST_BIGER_ONE_DAY_MSG);
        }
        return montlyEventValidationMessages;
    }

    private List<String> commonEventValidation(EventDto eventDto) {
        List<String> commonErrorMessage = new ArrayList<>();
        if (!isTitleValid(eventDto.getName())) {
            commonErrorMessage.add(ValidationConstants.EVENT_TITLE_ERROR_MSG);
        }
        if (!isDescriptionValid(eventDto.getDescription())) {
            commonErrorMessage.add(ValidationConstants.EVENT_DESCRIPTION_LENGTH_ERROR_MSG);
        }
        if (!isColorValid(eventDto.getColor())) {
            commonErrorMessage.add(ValidationConstants.EVENT_INVALID_COLOR);
        }
        if (!isTimeAndDateFormatValid(eventDto.getStartTime(), eventDto.getEndTime())) {
            commonErrorMessage.add(ValidationConstants.EVENT_DATE_ERROR_PARSING);
        } else {
            if (!isEventNotCreateInThePast(eventDto.getStartTime())) {
                commonErrorMessage.add(ValidationConstants.EVENT_PAST_TIME_CREATION_MSG);
            }
            if (!isEndTimeBiggerThanStart(eventDto.getStartTime(), eventDto.getEndTime())) {
                commonErrorMessage.add(ValidationConstants.EVENT_END_MUST_BIGGER_ONE_MINUTE_MSG);
            }
            if (!isCreatingEventInRoomWorkingHoursRange(eventDto)) {
                commonErrorMessage.add(ValidationConstants.OUT_OF_WORKING_HOURS);
            }
        }
        return commonErrorMessage;
    }

    private boolean isEventCreateInActiveRoom(long id) {
        return roomService.findByIdTransactional(id).isActive();
    }

    private boolean isEndDateBiggerThanStartDate(String startDate, String endDate) {
        LocalDate startTime = LocalDateTime.parse(startDate).toLocalDate();
        LocalDate endTime = LocalDateTime.parse(endDate).toLocalDate();
        return startTime.isBefore(endTime);
    }

    private boolean isDateEquals(String startDate, String endDate) {
        LocalDate startTime = LocalDateTime.parse(startDate).toLocalDate();
        LocalDate endTime = LocalDateTime.parse(endDate).toLocalDate();
        return startTime.equals(endTime);
    }

    private boolean isEventNotCreateInThePast(String date) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.parse(date);
        return !startTime.isBefore(currentDate);
    }

    private boolean isTimeAndDateFormatValid(String startDate, String endDate) {
        return Pattern.matches(DateConstants.DATE_T_TIME_REGEXP, startDate)
                && Pattern.matches(DateConstants.DATE_T_TIME_REGEXP, endDate);
    }

    private boolean isColorValid(String color) {
        return DateConstants.EventColors.getEventColors().contains(color);
    }

    private boolean isDescriptionValid(String description) {
        return description.length() <= ValidationConstants.EVENT_DESCRIPTION_MAX_LENGHT;
    }

    private boolean isTitleValid(String title) {
        return !((!StringUtils.hasText(title))
                || (title.length() > ValidationConstants.EVENT_TITLE_MAX_LENGHT));
    }

    private boolean isEndTimeBiggerThanStart(String startDate, String endDate) {
        LocalTime startTime = LocalDateTime.parse(startDate).toLocalTime();
        LocalTime endTime = LocalDateTime.parse(endDate).toLocalTime();
        return startTime.isBefore(endTime);
    }

    private boolean isCreatingEventInRoomWorkingHoursRange(EventDto eventDto) {
        LocalTime roomStartTime = LocalTime.parse(roomService
                .findByIdTransactional(eventDto.getRoomId()).getWorkingHoursStart());
        LocalTime roomEndTime = LocalTime.parse(roomService
                .findByIdTransactional(eventDto.getRoomId()).getWorkingHoursEnd());
        LocalTime startTime = LocalDateTime.parse(eventDto.getStartTime()).toLocalTime();
        LocalTime endTime = LocalDateTime.parse(eventDto.getEndTime()).toLocalTime();
        return !((startTime.isBefore(roomStartTime)) || (roomEndTime.isBefore(endTime)));
    }

    private boolean isReccurendDaysCorrected(RecurrentEventDto recurrentEventDto) {
        List<String> daysOfWeek = Arrays.asList(recurrentEventDto.getDaysOfWeek().split(" "));
        List<String> correctDaysOfWeek = DateConstants.ListDaysOfWeek.getListDaysOfWeek();
        List<String> errorDays = daysOfWeek.stream()
                .filter(e -> (correctDaysOfWeek.stream()
                        .filter(d -> d.equals(e))
                        .count()) < 1)
                .collect(Collectors.toList());
        return errorDays.size() == 0;
    }

}
