package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.MonthlyEventDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.service.EventService;
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

    private DateFormat onlyDateSimpleDateFormat = new SimpleDateFormat(ValidationConstants.DATE_FORMAT);
    private Date startDate;
    private Date endDate;
    private Date nowDate;
    private Calendar startDateCalendar;
    private Calendar endDateCalendar;


    private void dateSet(EventDto eventDto) throws ParseException {
        DateFormat sdf = new SimpleDateFormat(ValidationConstants.DATE_FORMAT);
        onlyDateSimpleDateFormat = new SimpleDateFormat(ValidationConstants.ONLY_DATE_FORMAT);
        startDate = sdf.parse(eventDto.getStartTime());
        endDate = sdf.parse(eventDto.getEndTime());
        nowDate = new Date();
        startDateCalendar = Calendar.getInstance();
        endDateCalendar = Calendar.getInstance();
        startDateCalendar.setTime(startDate);
        endDateCalendar.setTime(endDate);
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
            return onlyDateSimpleDateFormat.parse(eventDto.getStartTime()).equals(onlyDateSimpleDateFormat.parse(eventDto.getEndTime()));
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public void validate(Object o, Errors errors) {
        try {
            EventDto eventDto = (EventDto) o;
            dateSet(eventDto);
            ValidationUtils.rejectIfEmpty(errors, ValidationConstants.EVENT_TITLE, ValidationConstants.EVENT_EMPTY_TITLE_MSG);
            ValidationUtils.rejectIfEmpty(errors, ValidationConstants.START_TIME, ValidationConstants.EMPTY_FIELD_MSG);
            ValidationUtils.rejectIfEmpty(errors, ValidationConstants.END_TIME, ValidationConstants.EMPTY_FIELD_MSG);
            ValidationUtils.rejectIfEmpty(errors, ValidationConstants.EVENT_COLOR, ValidationConstants.EMPTY_FIELD_MSG);
            if (eventDto.getDescription().length() > ValidationConstants.EVENT_DESCRIPTION_MAX_LENGHT) {
                errors.rejectValue(ValidationConstants.EVENT_DESCRIPTION, ValidationConstants.EVENT_DESCRIPTION_LENGTH_ERROR_MSG);
            }
            if (!roomService.findById(eventDto.getRoomId()).isActive()) {
                errors.rejectValue(ValidationConstants.ROOM_ID, ValidationConstants.EVENT_INACTIVE_ROOM_ERROR_MSG);
            }
            if (startDate.before(nowDate)) {
                errors.rejectValue(ValidationConstants.START_TIME, ValidationConstants.EVENT_PAST_TIME_CREATION_MSG);
            }
            if (startDateCalendar.get(Calendar.HOUR_OF_DAY) > endDateCalendar.get(Calendar.HOUR_OF_DAY)) {
                errors.rejectValue(ValidationConstants.START_TIME, ValidationConstants.EVENT_END_MUST_BIGGER_ONE_MINUTE_MSG);
            } else if ((startDateCalendar.get(Calendar.HOUR_OF_DAY) == endDateCalendar.get(Calendar.HOUR_OF_DAY)) &&
                    ((startDateCalendar.get(Calendar.MINUTE) + ValidationConstants.ONE_MINUTE) > endDateCalendar.get(Calendar.MINUTE))) {
                errors.rejectValue(ValidationConstants.START_TIME, ValidationConstants.EVENT_END_MUST_BIGGER_ONE_MINUTE_MSG);
            }
            if (eventDto.getRecurrentType() == EventConstants.TypeOfRecurentEvent.MONTHLY) {
                MonthlyEventDto monthlyEventDto = (MonthlyEventDto) eventDto;
                if (monthlyEventDto.getDaysOfMonth().isEmpty()) {
                    errors.rejectValue(ValidationConstants.RECURRENT_DAYS, ValidationConstants.NO_DAYS_FOR_RECURRENT_EVENT);
                }
                if (!isMonthlyValid(monthlyEventDto)) {
                    errors.rejectValue(ValidationConstants.DATE_FIELD, ValidationConstants.DATE_DOESNT_EXIST);
                }
            } else if (eventDto.getRecurrentType() == EventConstants.TypeOfRecurentEvent.WEEKLY) {
                RecurrentEventDto weeklyEventDto = (RecurrentEventDto) eventDto;
                if (weeklyEventDto.getDaysOfWeek().isEmpty()) {
                    errors.rejectValue(ValidationConstants.RECURRENT_DAYS, ValidationConstants.NO_DAYS_FOR_RECURRENT_EVENT);
                }
            }
        } catch (ParseException e) {
            errors.rejectValue(ValidationConstants.START_TIME, ValidationConstants.EVENT_DATE_FORMAT_INVALID_MSG);
            errors.rejectValue(ValidationConstants.END_TIME, ValidationConstants.EVENT_DATE_FORMAT_INVALID_MSG);
        } catch (ClassCastException ex) {
            errors.rejectValue(ValidationConstants.EVENT_TITLE, ValidationConstants.EVENT_CAST_EXCEPTION);
        }
    }
}
