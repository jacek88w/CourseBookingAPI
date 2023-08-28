package pl.jacek.coursebookingresolving.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pl.jacek.coursebookingresolving.validation.DateOfBirthValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateOfBirthValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateOfBirth {
    String message() default "Invalid date of birth";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
