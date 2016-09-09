package ua.softserveinc.tc.validator.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * This annotation checks "is unique hours of rates and is it in right diapason" was entered.
 * <p>
 * Created by TARAS on 12.06.2016.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = RateValidationImpl.class)
public @interface RateValidation {

    String message() default "{RateValidation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
