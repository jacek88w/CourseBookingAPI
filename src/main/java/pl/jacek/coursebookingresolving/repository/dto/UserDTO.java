package pl.jacek.coursebookingresolving.repository.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    @Length(min = 3, max = 50)
    private String firstName;

    @NotNull
    @Length(min = 3, max = 50)
    private String lastName;

    @Min(1)
    @Max(150)
    private int age;

    @NotNull
    @Email
    private String email;

    @ToString.Exclude
    private String password;

    @ToString.Exclude
    private String matchingPassword;
}
