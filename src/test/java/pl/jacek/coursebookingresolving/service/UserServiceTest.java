package pl.jacek.coursebookingresolving.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jacek.coursebookingresolving.dto.UserDTO;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.repository.UserRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
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
}