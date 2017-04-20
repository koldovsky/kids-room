package ua.softserveinc.tc.validator;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.service.DayDiscountService;

@Component
public class DayDiscountValidateImpl implements DayDiscountValidate {

  @Autowired
  private DayDiscountService dayDiscountService;

  @Override
  public boolean supports(Class<?> aClass) {
    return DayDiscountDTO.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    if (o instanceof DayDiscountDTO) {
      DayDiscountDTO dayDiscountDTO = (DayDiscountDTO) o;
      //Check if null
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.DAY_DISCOUNT_REASON,
          ValidationConstants.EMPTY_DAY_DISCOUNT_REASON);
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.DAY_DISCOUNT_VALUE,
          ValidationConstants.EMPTY_DAY_DISCOUNT_VALUE);
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.DAY_DISCOUNT_START_DATE,
          ValidationConstants.EMPTY_DAY_DISCOUNT_START_DATE);
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.DAY_DISCOUNT_END_DATE,
          ValidationConstants.EMPTY_DAY_DISCOUNT_END_DATE);
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.DAY_DISCOUNT_START_TIME,
          ValidationConstants.EMPTY_DAY_DISCOUNT_START_TIME);
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.DAY_DISCOUNT_END_TIME,
          ValidationConstants.EMPTY_DAY_DISCOUNT_END_TIME);

      //REGEX
      String reasonDiscount = dayDiscountDTO.getReason();
      if (Objects.nonNull(reasonDiscount)) {
        if (!Pattern.compile(ValidationConstants.DISCOUNT_REASON_REGEX)
            .matcher(reasonDiscount)
            .matches()) {
          errors.rejectValue(ValidationConstants.DAY_DISCOUNT_REASON,
              ValidationConstants.DAY_DISCOUNT_WRONG_REASON);
        }
      }

      if (Objects.nonNull(dayDiscountDTO.getValue())) {
        if (!Pattern.compile(ValidationConstants.DISCOUNT_VALUE_REGEX)
            .matcher(dayDiscountDTO.getValue().toString())
            .matches()) {
          errors.rejectValue(ValidationConstants.DAY_DISCOUNT_VALUE,
              ValidationConstants.DAY_DISCOUNT_WRONG_VALUE);
        }
      }

      //Check all date-time validations
      LocalDate startDate = dayDiscountDTO.getStartDate();
      LocalDate endDate = dayDiscountDTO.getEndDate();
      LocalTime startTime = dayDiscountDTO.getStartTime();
      LocalTime endTime = dayDiscountDTO.getEndTime();

      if (Objects.nonNull(endDate) && Objects.nonNull(startDate)
          && Objects.nonNull(startTime) && Objects.nonNull(endTime)) {

        if (endDate.isBefore(startDate)) {
          errors.rejectValue(ValidationConstants.DAY_DISCOUNT_END_DATE,
              ValidationConstants.END_DATE_IS_BEFORE);
        }

        if (endTime.isBefore(startTime)) {
          errors.rejectValue(ValidationConstants.DAY_DISCOUNT_END_TIME,
              ValidationConstants.END_TIME_IS_BEFORE);
        }
        if (dayDiscountService
            .getDayDiscountsForValidate(startDate, endDate, startTime, endTime,
                dayDiscountDTO.getId()).size() > 0) {
          errors.rejectValue(ValidationConstants.DAY_DISCOUNT_END_TIME,
              ValidationConstants.DAY_DISCOUNT_ALREADY_HAVE);
        }
      }
    }
  }
}
