package pl.jacek.coursebookingresolving.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import pl.jacek.coursebookingresolving.enums.CourseAdvancement;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "tbl_course")
public class Course {

    public Course(String name, CourseAdvancement advancement, User teacher) {
        this.name = name;
        this.advancement = advancement;
        this.teacher = teacher;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Length(min = 2, max = 50)
    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    private CourseAdvancement advancement;

    @ManyToOne(
        fetch = FetchType.EAGER
    )
    private User teacher;
}