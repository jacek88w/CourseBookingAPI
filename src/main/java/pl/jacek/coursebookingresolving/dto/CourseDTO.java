package pl.jacek.coursebookingresolving.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.enums.CourseAdvancement;

@AllArgsConstructor
@Data
public class CourseDTO {

    private String name;
    private CourseAdvancement advancement;

    private User teacher;
}
