package ua.softserveinc.tc.validator;

import org.springframework.validation.Errors;
import ua.softserveinc.tc.dto.AbonnementDto;

public class AbonnementsValidatorImpl implements AbonnementsValidator {

    @Override
    public boolean supports(Class<?> aClass) {
        return AbonnementDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof AbonnementDto) {
            AbonnementDto abonnementToValidate = (AbonnementDto) o;
        }
    }
}
