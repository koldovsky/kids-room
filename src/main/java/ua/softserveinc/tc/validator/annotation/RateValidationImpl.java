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
 * <p>
 * Created by TARAS on 12.06.2016.
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

        if (rates.stream().filter(rate -> (rate.getHourRate() == null || rate.getPriceRate() == null))
                .findFirst().isPresent()) {
            return false;
        }
        if (rates.stream().filter(rate -> (rate.getHourRate() > 24 || rate.getHourRate() < 1)).findFirst().isPresent()) {
            return false;
        }
        if (rates.stream().filter(rate -> (rate.getPriceRate() < 0)).findFirst().isPresent()){
            return false;
        }
        Map<Integer, Long> map = rates.stream().collect(Collectors.groupingBy(Rate::getHourRate, Collectors.counting()));
        return !map.values().stream().filter(en -> en > 1).findFirst().isPresent();
    }
}
