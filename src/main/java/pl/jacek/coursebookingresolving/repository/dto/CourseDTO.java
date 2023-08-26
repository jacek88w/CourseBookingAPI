package pl.jacek.coursebookingresolving.repository.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.enums.CourseAdvancement;

@AllArgsConstructor
@Data
public class CourseDTO {

    @Length(min = 2, max = 50)
    @NotNull
    private String name;
    private CourseAdvancement advancement;

    private User teacher;
}
