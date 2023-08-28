package pl.jacek.coursebookingresolving.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jacek.coursebookingresolving.entity.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseModel {

    private String token;
    private User user;
}
