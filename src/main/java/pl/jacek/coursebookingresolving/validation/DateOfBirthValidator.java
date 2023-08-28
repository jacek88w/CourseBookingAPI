package pl.jacek.coursebookingresolving.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.jacek.coursebookingresolving.validation.annotations.ValidDateOfBirth;

import java.time.LocalDate;

public class DateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, LocalDate> {

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return true; // <- already @NotNull
        }

        LocalDate now = LocalDate.now();
        return !(dateOfBirth.isAfter(now) || dateOfBirth.isBefore(LocalDate.parse("1900-01-01")));
    }


}
