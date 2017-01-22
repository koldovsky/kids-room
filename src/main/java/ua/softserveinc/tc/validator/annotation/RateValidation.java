package ua.softserveinc.tc.validator.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * This annotation checks "is unique hours of rates and is it in right diapason" was entered.
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
