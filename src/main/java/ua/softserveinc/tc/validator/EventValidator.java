package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.MonthlyEventDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.entity.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


@Component
public class EventValidator implements Validator {
    @Autowired
    private RoomService roomService;

    private DateFormat onlyDateSimpleDateFormat;
    private Date startDate;
    private Date endDate;
    private Date nowDate;
    private Date onlyStartDate;
    private Date onlyEndDate;
    private Calendar startDateCalendar;
    private Calendar endDateCalendar;

    private boolean isValidColor(String colorCode) {
        boolean validColor = false;
        for (int i = 0; i < DateConstants.EventColors.getEventColors().size(); i++) {
            if (Objects.equals(colorCode, DateConstants.EventColors.getEventColors().get(i))) {
                validColor = true;
                break;
            }

        }
        return validColor;
    }
    private void dateSet(EventDto eventDto) throws ParseException {
        DateFormat sdf = new SimpleDateFormat(ValidationConstants.DATE_FORMAT);
        onlyDateSimpleDateFormat = new SimpleDateFormat(ValidationConstants.ONLY_DATE_FORMAT);
        startDate = sdf.parse(eventDto.getStartTime());
        endDate = sdf.parse(eventDto.getEndTime());
        onlyStartDate = onlyDateSimpleDateFormat.parse(eventDto.getStartTime());
        onlyEndDate = onlyDateSimpleDateFormat.parse(eventDto.getEndTime());
        nowDate = new Date();
        startDateCalendar = Calendar.getInstance();
        endDateCalendar = Calendar.getInstance();
        startDateCalendar.setTime(startDate);
        endDateCalendar.setTime(endDate);
    }

    public List<String> logicValidEvent(EventDto eventDto){
        List<String> logicError = new ArrayList<>();
        if (!roomService.findByIdTransactional(eventDto.getRoomId()).isActive()) {
          logicError.add(ValidationConstants.EVENT_INACTIVE_ROOM_ERROR_MSG);
        }
        return  logicError;
    }

    public List<String> generalValidateEvent(EventDto eventDto) {
        List<String> generalError = new ArrayList<>();
        if (eventDto.getName() == null || !StringUtils.hasText(eventDto.getName())){
            generalError.add(ValidationConstants.EVENT_EMPTY_TITLE_MSG);
        }
        if (eventDto.getName().length() > ValidationConstants.EVENT_DESCRIPTION_MAX_LENGHT){
            generalError.add(ValidationConstants.EVENT_MAX_TITLE_LENGHT);
        }
        if (eventDto.getDescription().length() > ValidationConstants.EVENT_DESCRIPTION_MAX_LENGHT){
            generalError.add(ValidationConstants.EVENT_DESCRIPTION_LENGTH_ERROR_MSG);
        }
        if (!isValidColor(eventDto.getColor())) {
            generalError.add(ValidationConstants.EVENT_INVALID_COLOR);
        }
        try {
            dateSet(eventDto);
        } catch (ParseException e) {
            generalError.add(ValidationConstants.EVENT_DATE_ERROR_PARSING);
            return generalError;
        }
        if (startDate.before(nowDate)) {
               generalError.add(ValidationConstants.EVENT_PAST_TIME_CREATION_MSG);
        }
        if (startDateCalendar.get(Calendar.HOUR_OF_DAY) >
            endDateCalendar.get(Calendar.HOUR_OF_DAY)) {
            generalError.add(ValidationConstants.EVENT_START_TIME_BIGGER_END_MSG);
        } else if ((startDateCalendar.get(Calendar.HOUR_OF_DAY) ==
            endDateCalendar.get(Calendar.HOUR_OF_DAY)) &&
            ((startDateCalendar.get(Calendar.MINUTE) + ValidationConstants.ONE_MINUTE) >
                endDateCalendar.get(Calendar.MINUTE))) {
          generalError.add(ValidationConstants.EVENT_START_TIME_BIGGER_END_MSG);
        }

        if (eventDto.getRecurrentId() == null) {
          if (!(onlyStartDate.getTime() == onlyEndDate.getTime()))
              generalError.add(ValidationConstants.EVENT_START_NOT_EQUALS_END_MSG);
        } else if (!(onlyStartDate.before(onlyEndDate))){
            generalError.add(ValidationConstants.EVENT_RECURRENT_START_NOT_BEFORE_END);
        }
        return generalError;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.equals(aClass);
    }

    public boolean isReccurrentValid(RecurrentEventDto recurrentEventDto) {
        try {
            dateSet(recurrentEventDto);
            return onlyDateSimpleDateFormat.parse(recurrentEventDto.getStartTime()).before(
                    onlyDateSimpleDateFormat.parse(recurrentEventDto.getEndTime()));
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isMonthlyValid(MonthlyEventDto monthlyEventDto) {
        try {
            dateSet(monthlyEventDto);
            return onlyDateSimpleDateFormat.parse(monthlyEventDto.getStartTime()).before(
                    onlyDateSimpleDateFormat.parse(monthlyEventDto.getEndTime()));
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isSingleValid(EventDto eventDto) {
        try {
            dateSet(eventDto);
            return onlyDateSimpleDateFormat.parse(eventDto.getStartTime()).equals(
                    onlyDateSimpleDateFormat.parse(eventDto.getEndTime()));
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public void validate(Object o, Errors errors) {
        try {
            EventDto eventDto = (EventDto) o;
            dateSet(eventDto);
            ValidationUtils.rejectIfEmpty(errors,
                    ValidationConstants.EVENT_TITLE, ValidationConstants.EVENT_EMPTY_TITLE_MSG);
            ValidationUtils.rejectIfEmpty(errors,
                    ValidationConstants.START_TIME, ValidationConstants.EMPTY_FIELD_MSG);
            ValidationUtils.rejectIfEmpty(errors,
                    ValidationConstants.END_TIME, ValidationConstants.EMPTY_FIELD_MSG);
            ValidationUtils.rejectIfEmpty(errors,
                    ValidationConstants.EVENT_COLOR, ValidationConstants.EMPTY_FIELD_MSG);
            if (eventDto.getDescription().length() >
                    ValidationConstants.EVENT_DESCRIPTION_MAX_LENGHT) {
                errors.rejectValue(ValidationConstants.EVENT_DESCRIPTION,
                        ValidationConstants.EVENT_DESCRIPTION_LENGTH_ERROR_MSG);
            }
            if (!roomService.findByIdTransactional(eventDto.getRoomId()).isActive()) {
                errors.rejectValue(ValidationConstants.ROOM_ID,
                        ValidationConstants.EVENT_INACTIVE_ROOM_ERROR_MSG);
            }
            if (startDate.before(nowDate)) {
                errors.rejectValue(ValidationConstants.START_TIME,
                        ValidationConstants.EVENT_PAST_TIME_CREATION_MSG);
            }
            if (startDateCalendar.get(Calendar.HOUR_OF_DAY) >
                    endDateCalendar.get(Calendar.HOUR_OF_DAY)) {
                errors.rejectValue(ValidationConstants.START_TIME,
                        ValidationConstants.EVENT_END_MUST_BIGGER_ONE_MINUTE_MSG);
            } else if ((startDateCalendar.get(Calendar.HOUR_OF_DAY) ==
                    endDateCalendar.get(Calendar.HOUR_OF_DAY)) &&
                    ((startDateCalendar.get(Calendar.MINUTE) + ValidationConstants.ONE_MINUTE) >
                            endDateCalendar.get(Calendar.MINUTE))) {
                errors.rejectValue(ValidationConstants.START_TIME,
                        ValidationConstants.EVENT_END_MUST_BIGGER_ONE_MINUTE_MSG);
            }
            if (eventDto.getRecurrentType() == EventConstants.TypeOfRecurentEvent.MONTHLY) {
                MonthlyEventDto monthlyEventDto = (MonthlyEventDto) eventDto;
                if (monthlyEventDto.getDaysOfTheMonth() == null ||
                        monthlyEventDto.getDaysOfTheMonth().isEmpty()) {
                    errors.rejectValue(ValidationConstants.MONTH_RECURRENT_DAYS,
                            ValidationConstants.NO_DAYS_FOR_RECURRENT_EVENT);
                }
                if (!isMonthlyValid(monthlyEventDto)) {
                    errors.rejectValue(ValidationConstants.MONTH_RECURRENT_DAYS,
                            ValidationConstants.EVENT_RECCURRENT_END_MUST_BIGER_ONE_DAY_MSG);
                }
            } else if (eventDto.getRecurrentType() ==
                    EventConstants.TypeOfRecurentEvent.WEEKLY) {
                RecurrentEventDto weeklyEventDto = (RecurrentEventDto) eventDto;
                if (weeklyEventDto.getDaysOfWeek() == null ||
                        weeklyEventDto.getDaysOfWeek().isEmpty()) {
                    errors.rejectValue(ValidationConstants.WEEK_RECURRENT_DAYS,
                            ValidationConstants.NO_DAYS_FOR_RECURRENT_EVENT);
                }
            }
        } catch (ParseException e) {
            errors.rejectValue(ValidationConstants.START_TIME,
                    ValidationConstants.EVENT_DATE_FORMAT_INVALID_MSG);
            errors.rejectValue(ValidationConstants.END_TIME,
                    ValidationConstants.EVENT_DATE_FORMAT_INVALID_MSG);
        } catch (ClassCastException ex) {
            errors.rejectValue(ValidationConstants.EVENT_TITLE,
                    ValidationConstants.EVENT_CAST_EXCEPTION);
        }
    }
}
