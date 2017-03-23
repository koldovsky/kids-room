package ua.softserveinc.tc.validator;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.dto.AbonnementDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import ua.softserveinc.tc.constants.ValidationConstants;
import java.util.regex.Pattern;


@Component
public class AbonnementsValidatorImpl implements AbonnementsValidator {

    @Override
    public boolean supports(Class<?> aClass) {
        return AbonnementDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof AbonnementDto) {
            AbonnementDto abonnementToValidate = (AbonnementDto) o;
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.ABONNEMENT_NAME, ValidationConstants.ABONNEMENT_EMPTY_FIELD);
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.ABONNEMENT_HOUR, ValidationConstants.ABONNEMENT_EMPTY_FIELD);
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, ValidationConstants.ABONNEMENT_PRICE, ValidationConstants.ABONNEMENT_EMPTY_FIELD);

            if (!Pattern.compile(ValidationConstants.NAME_REGEX)
                    .matcher(abonnementToValidate.getName())
                    .matches()) {
                errors.rejectValue(ValidationConstants.ABONNEMENT_NAME, ValidationConstants.ABONNEMENT_INVALID_NAME_MSG);
            }

            int abonnementNameLength = abonnementToValidate.getName().length();
            if ((abonnementNameLength < ValidationConstants.ABONNEMENT_NAME_MIN_CHARACTERS) ||
                    (abonnementNameLength > ValidationConstants.ABONNEMENT_NAME_MAX_CHARACTERS)) {
                errors.rejectValue(ValidationConstants.ABONNEMENT_NAME, ValidationConstants.ABONNEMENT_NAME_LENGHT);
            }
            int abonnementHourVal = abonnementToValidate.getHour();
            if ((abonnementHourVal < ValidationConstants.ABONNEMENT_HOUR_MIN_VALUE) ||
                    (abonnementHourVal > ValidationConstants.ABONNEMENT_HOUR_MAX_VALUE)) {
                errors.rejectValue(ValidationConstants.ABONNEMENT_HOUR, ValidationConstants.ABONNEMENT_HOUR_LENGTH);
            }
            long abonnementPriceVal = abonnementToValidate.getPrice();
            if (abonnementPriceVal < ValidationConstants.ABONNEMENT_PRICE_MIN_VALUE) {
                errors.rejectValue(ValidationConstants.ABONNEMENT_PRICE, ValidationConstants.ABONNEMENT_PRICE_SIZE);
            }
        }
    }
}
