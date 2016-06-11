package ua.softserveinc.tc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.ConfigurationDto;

/**
 * Created by Nestor on 11.06.2016.
 */

@Component
public class ConfigValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ConfigurationDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ConfigurationDto dto = (ConfigurationDto) o;
        //TODO: try reflection
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConstants.CONFIG_FIELDS.CALCULATION_HOUR, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConstants.CONFIG_FIELDS.CALCULATION_MINUTE, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConstants.CONFIG_FIELDS.EMAIL_REPORT_DAY, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConstants.CONFIG_FIELDS.EMAIL_REPORT_HOUR, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConstants.CONFIG_FIELDS.EMAIL_REPORT_MINUTE, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConstants.CONFIG_FIELDS.MAX_AGE, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConstants.CONFIG_FIELDS.MIN_AGE, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConstants.CONFIG_FIELDS.MIN_PERIOD, ValidationConstants.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConstants.CONFIG_FIELDS.SERVER_NAME, ValidationConstants.EMPTY_FIELD_MSG);

        if(dto.getDayToSendEmailReport() > 28 || dto.getDayToSendEmailReport() < 1){
            errors.rejectValue(ValidationConstants.CONFIG_FIELDS.EMAIL_REPORT_DAY,
                    ValidationConstants.CONFIG_FIELDS.NOT_VALID_DATE_MSG);
        }

        if(dto.getHourToSendEmailReport() > 23 || dto.getHourToSendEmailReport() < 0){
            errors.rejectValue(ValidationConstants.CONFIG_FIELDS.EMAIL_REPORT_HOUR,
                    ValidationConstants.CONFIG_FIELDS.NOT_VALID_TIME_MSG);
        }

        if (dto.getMinutesToSendEmailReport() > 59 || dto.getMinutesToSendEmailReport() < 0) {
            errors.rejectValue(ValidationConstants.CONFIG_FIELDS.EMAIL_REPORT_MINUTE,
                    ValidationConstants.CONFIG_FIELDS.NOT_VALID_TIME_MSG);
        }

        if(dto.getHourToCalculateBookingsEveryDay() > 23 || dto.getHourToCalculateBookingsEveryDay() < 0){
            errors.rejectValue(ValidationConstants.CONFIG_FIELDS.CALCULATION_HOUR,
                    ValidationConstants.CONFIG_FIELDS.NOT_VALID_TIME_MSG);
        }

        if (dto.getMinutesToCalculateBookingsEveryDay() > 59 || dto.getMinutesToCalculateBookingsEveryDay() < 0) {
            errors.rejectValue(ValidationConstants.CONFIG_FIELDS.CALCULATION_MINUTE,
                    ValidationConstants.CONFIG_FIELDS.NOT_VALID_TIME_MSG);
        }

        if(dto.getMinPeriodSize() < 1){
            errors.rejectValue(ValidationConstants.CONFIG_FIELDS.MIN_PERIOD,
                    ValidationConstants.CONFIG_FIELDS.NOT_VALID_TIME_MSG);
        }

    }
}
