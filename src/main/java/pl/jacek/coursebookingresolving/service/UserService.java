package pl.jacek.coursebookingresolving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jacek.coursebookingresolving.dto.UserDTO;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.enums.Role;
import pl.jacek.coursebookingresolving.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(UserDTO userDTO) {
        var user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .dateOfBirth(userDTO.getDateOfBirth())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        //var jwtToken = jwtService.generateToken(user);
        return user;
    }
}
