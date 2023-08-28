package pl.jacek.coursebookingresolving.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    private String firstname;

    private String lastname;

    private LocalDate dateOfBirth;

    private String email;

    private String password;

    private String matchingPassword;
}
