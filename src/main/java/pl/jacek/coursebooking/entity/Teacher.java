package pl.jacek.coursebooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import pl.jacek.coursebooking.exceptions.EntityValidationException;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(
        name = "tbl_teacher",
        uniqueConstraints = @UniqueConstraint(
                name = "email_address_unique",
                columnNames = "email_address"
        )
)
public class Teacher {

    public Teacher(String firstName, String lastName, int age, String email, List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.courses = courses;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Length(min = 3, max = 50)
    private String firstName;

    @NotNull
    @Length(min = 3, max = 50)
    private String lastName;

    @Min(0)
    @Max(150)
    private int age;

    @Email
    @Column(name = "email_address")
    @NotNull
    private String email;

    @OneToMany(
                    fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "teacher_id",
            referencedColumnName = "id"
    )
    private List<Course> courses;

    public void setFirstName(String firstName) {
        if(!(firstName.length() > 2 && firstName.length() < 51)) {
            throw new EntityValidationException("Length of firstName in: Teacher, should be between 3 and 50");
        } else {
            this.firstName = firstName;
        }
    }

    public void setLastName(String lastName) {
        if(!(lastName.length() > 2 && lastName.length() < 51)) {
            throw new EntityValidationException("Length of lastName in: Teacher, should be between 3 and 50");
        } else {
            this.lastName = lastName;
        }
    }

    public void setAge(int age) {
        if(age < 0 || age > 150) {
            throw new EntityValidationException("Age in: Teacher, should be between 1 and 150");
        } else {
            this.age = age;
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
