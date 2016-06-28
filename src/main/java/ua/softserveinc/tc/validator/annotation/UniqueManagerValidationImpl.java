package ua.softserveinc.tc.validator.annotation;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.util.JsonUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class validator for validation of input managers of room.
 * <p>
 * Created by TARAS on 17.06.2016.
 */
@Component
public class UniqueManagerValidationImpl implements ConstraintValidator<UniqueManagerValidation, String> {


    @Override
    public void initialize(UniqueManagerValidation constraintAnnotation) {

    }


    /**
     * Method checks for compliance requirements of input manager value.
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<UserDto> managers = JsonUtil.fromJsonList(value, UserDto[].class);

        if (managers.stream().filter(manager -> (manager.getId() == null)).findFirst().isPresent()) {
            return false;
        }
        Map<Long, Long> map = managers.stream().collect(Collectors.groupingBy(UserDto::getId, Collectors.counting()));
        return !map.values().stream().filter(en -> en > 1).findFirst().isPresent();
    }
}