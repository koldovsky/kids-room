package ua.softserveinc.tc.validator;


import java.time.LocalTime;
import java.util.Objects;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.PersonalDiscountDTO;
import ua.softserveinc.tc.service.PersonalDiscountService;

@Component
public class PersonalDiscountValidateImpl implements PersonalDiscountValidate {

  @Autowired
  private PersonalDiscountService personalDiscountService;

  @Override
  public boolean supports(Class<?> aClass) {
    return PersonalDiscountDTO.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    if (o instanceof PersonalDiscountDTO) {
      PersonalDiscountDTO personalDiscountDTO = (PersonalDiscountDTO) o;
      //Check if null
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.DAY_DISCOUNT_VALUE,
          ValidationConstants.EMPTY_DAY_DISCOUNT_VALUE);
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.DAY_DISCOUNT_START_TIME,
          ValidationConstants.EMPTY_DAY_DISCOUNT_START_TIME);
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.DAY_DISCOUNT_END_TIME,
          ValidationConstants.EMPTY_DAY_DISCOUNT_END_TIME);

      if (Objects.nonNull(personalDiscountDTO.getValue())) {
        if (!Pattern.compile(ValidationConstants.DISCOUNT_VALUE_REGEX)
            .matcher(personalDiscountDTO.getValue().toString())
            .matches()) {
          errors.rejectValue(ValidationConstants.DAY_DISCOUNT_VALUE,
              ValidationConstants.DAY_DISCOUNT_WRONG_VALUE);
        }
      }

      //Check all date-time validations
      LocalTime startTime = personalDiscountDTO.getStartTime();
      LocalTime endTime = personalDiscountDTO.getEndTime();

      if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
        if (endTime.isBefore(startTime)) {
          errors.rejectValue(ValidationConstants.DAY_DISCOUNT_END_TIME,
              ValidationConstants.END_TIME_IS_BEFORE);
        }

        if (personalDiscountService
            .getPersonalDiscountForValidate(startTime, endTime,
                personalDiscountDTO.getUser().getId(),personalDiscountDTO.getId()).size() > 0) {
          errors.rejectValue(ValidationConstants.DAY_DISCOUNT_END_TIME,
              ValidationConstants.DAY_DISCOUNT_ALREADY_HAVE);
        }
      }
    }
  }
}
