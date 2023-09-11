package pl.jacek.coursebookingresolving.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jacek.coursebookingresolving.config.ApplicationConfig;
import pl.jacek.coursebookingresolving.dto.UserDTO;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.repository.UserRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ApplicationConfig applicationConfig;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .firstName("Jacek")
                .lastName("Popołenko")
                .dateOfBirth(LocalDate.of(2000, 10, 20))
                .email("Jacek@gmail.com")
                .password("1234")
                .build();

        userDTO = UserDTO.builder()
                .firstName("Jacek")
                .lastName("Popołenko")
                .dateOfBirth(LocalDate.of(2000, 10, 20))
                .email("Jacek@gmail.com")
                .password("1234")
                .build();
    }

    @Test
    public void UserService_RegisterUser_ReturnUser() {
        lenient().when(userRepository.save(user)).thenReturn(user);
        lenient().when(passwordEncoder.encode("1234")).thenReturn("a");

        User savedUser = userService.register(userDTO);

        assertThat(savedUser).isNotNull();
    }

    @Test
    public void UserService_FindUserById_ReturnUser() {

    }

}