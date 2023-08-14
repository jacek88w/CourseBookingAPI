package pl.jacek.coursebooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pl.jacek.coursebooking.entity.Teacher;
import pl.jacek.coursebooking.enums.CourseAdvancement;

@AllArgsConstructor
@Data
public class CourseDTO {

    @Length(min = 2, max = 50)
    @NotNull
    private String name;
    private CourseAdvancement advancement;

    private Teacher teacher;
}
