package ua.softserveinc.tc.validator.annotation;

import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.util.JsonUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class validator for validation of input rates of room.
 */
@Component
public class RateValidationImpl implements ConstraintValidator<RateValidation, String> {


    @Override
    public void initialize(RateValidation constraintAnnotation) {

    }


    /**
     * Method checks for compliance requirements of input rate value.
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<Rate> rates = JsonUtil.fromJsonList(value, Rate[].class);
        if (rates.stream().anyMatch(rate -> (rate.getHourRate() == null || rate.getPriceRate() == null))) {
            return false;
        }
        if (rates.stream().anyMatch(rate -> (rate.getHourRate() > 24 || rate.getHourRate() < 1))) {
            return false;
        }
        if (rates.stream().anyMatch(rate -> (rate.getPriceRate() < 0))){
            return false;
        }
        Map<Integer, Long> map = rates.stream().collect(Collectors.groupingBy(Rate::getHourRate, Collectors.counting()));
        return map.values().stream().noneMatch(en -> en > 1);
    }
}
