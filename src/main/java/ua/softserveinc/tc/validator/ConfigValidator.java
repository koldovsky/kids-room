package ua.softserveinc.tc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.ValidationConst;
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
                ValidationConst.CONFIG_FIELDS.CALCULATION_HOUR, ValidationConst.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConst.CONFIG_FIELDS.CALCULATION_MINUTE, ValidationConst.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConst.CONFIG_FIELDS.EMAIL_REPORT_DAY, ValidationConst.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConst.CONFIG_FIELDS.EMAIL_REPORT_HOUR, ValidationConst.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConst.CONFIG_FIELDS.EMAIL_REPORT_MINUTE, ValidationConst.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConst.CONFIG_FIELDS.MAX_AGE, ValidationConst.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConst.CONFIG_FIELDS.MIN_AGE, ValidationConst.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConst.CONFIG_FIELDS.MIN_PERIOD, ValidationConst.EMPTY_FIELD_MSG);
        ValidationUtils.rejectIfEmpty(errors,
                ValidationConst.CONFIG_FIELDS.SERVER_NAME, ValidationConst.EMPTY_FIELD_MSG);

        if(dto.getDayToSendEmailReport() > 28 || dto.getDayToSendEmailReport() < 1){
            errors.rejectValue(ValidationConst.CONFIG_FIELDS.EMAIL_REPORT_DAY,
                    ValidationConst.CONFIG_FIELDS.NOT_VALID_DATE_MSG);
        }

        if(dto.getHourToSendEmailReport() > 23 || dto.getHourToSendEmailReport() < 0){
            errors.rejectValue(ValidationConst.CONFIG_FIELDS.EMAIL_REPORT_HOUR,
                    ValidationConst.CONFIG_FIELDS.NOT_VALID_TIME_MSG);
        }

        if (dto.getMinutesToSendEmailReport() > 59 || dto.getMinutesToSendEmailReport() < 0) {
            errors.rejectValue(ValidationConst.CONFIG_FIELDS.EMAIL_REPORT_MINUTE,
                    ValidationConst.CONFIG_FIELDS.NOT_VALID_TIME_MSG);
        }

        if(dto.getHourToCalculateBookingsEveryDay() > 23 || dto.getHourToCalculateBookingsEveryDay() < 0){
            errors.rejectValue(ValidationConst.CONFIG_FIELDS.CALCULATION_HOUR,
                    ValidationConst.CONFIG_FIELDS.NOT_VALID_TIME_MSG);
        }

        if (dto.getMinutesToCalculateBookingsEveryDay() > 59 || dto.getMinutesToCalculateBookingsEveryDay() < 0) {
            errors.rejectValue(ValidationConst.CONFIG_FIELDS.CALCULATION_MINUTE,
                    ValidationConst.CONFIG_FIELDS.NOT_VALID_TIME_MSG);
        }

        if(dto.getMinPeriodSize() < 1){
            errors.rejectValue(ValidationConst.CONFIG_FIELDS.MIN_PERIOD,
                    ValidationConst.CONFIG_FIELDS.NOT_VALID_TIME_MSG);
        }

    }
}
