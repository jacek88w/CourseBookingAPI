package pl.jacek.coursebookingresolving.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.exceptions.NoSuchEntityException;
import pl.jacek.coursebookingresolving.repository.UserRepository;

import pl.jacek.coursebookingresolving.dto.UserDTO;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NoSuchEntityException("User not found");
        } //todo duplicated
    }

    public String deleteById(Long id) {
        userRepository.deleteById(id);
        return "User with ID " + id + " deleted";
    }

    public String deleteAll() {
        userRepository.deleteAll();
        return "All users deleted";
    }

    public String update(UserDTO userDTO, Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User userDB;
        if(userOptional.isPresent()) {
            userDB = userOptional.get();
        } else {
            throw new NoSuchEntityException("User not found");
        }

        if(Objects.nonNull(userDTO.getFirstname()) &&
            !"".equalsIgnoreCase(userDTO.getFirstname())) { // <-- FirstName
            userDB.setFirstName(userDTO.getFirstname());
        }

        if(Objects.nonNull(userDTO.getLastname()) &&
                !"".equalsIgnoreCase(userDTO.getLastname())) { // <-- LastName
            userDB.setLastName(userDTO.getLastname());
        }

        if(Objects.nonNull(userDTO.getDateOfBirth())) {
            userDB.setDateOfBirth(userDTO.getDateOfBirth()); // <-- Age
        }

        if(Objects.nonNull(userDTO.getEmail()) &&
                !"".equalsIgnoreCase(userDTO.getEmail())) { // <-- Email
            userDB.setEmail(userDTO.getEmail());
        }

//        if(Objects.nonNull(userDTO.getCourses()) &&
//                !(userDB.getCourses().isEmpty())) { // <-- Courses
//            userDB.setCourses(userDTO.getCourses());
//        }
        userRepository.save(userDB);
        return "User with ID: " + id + " updated";
    }

//    public void saveVerificationTokenForUser(String token, User user) {
//        VerificationToken verificationToken
//                = new VerificationToken(user, token);
//
//        verificationTokenRepository.save(verificationToken);
//    }
//
//    public String validateVerificationToken(String token) {
//        VerificationToken verificationToken
//                = verificationTokenRepository.findByToken(token);
//
//        if (verificationToken == null) {
//            return "invalid";
//        }
//
//        User user = verificationToken.getUser();
//        Calendar cal = Calendar.getInstance();
//
//        if ((verificationToken.getExpirationTime().getTime()
//                - cal.getTime().getTime()) <= 0) {
//            verificationTokenRepository.delete(verificationToken);
//            return "expired";
//        }
//
//        user.setEnabled(true);
//        userRepository.save(user);
//        return "valid";
//    }
//
//    public VerificationToken generateNewVerificationToken(String oldToken) {
//        VerificationToken verificationToken
//                = verificationTokenRepository.findByToken(oldToken);
//        verificationToken.setToken(UUID.randomUUID().toString());
//        verificationTokenRepository.save(verificationToken);
//        return verificationToken;
//    }
//
//    public User findUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    public void createPasswordResetTokenForUser(User user, String token) {
//        PasswordResetToken passwordResetToken
//                = new PasswordResetToken(user, token);
//        passwordResetTokenRepository.save(passwordResetToken);
//    }
//
//    public String validatePasswordResetToken(String token) {
//        PasswordResetToken passwordResetToken
//                = passwordResetTokenRepository.findByToken(token);
//
//        if (passwordResetToken == null) {
//            return "invalid";
//        }
//
//        User user = passwordResetToken.getUser();
//        Calendar cal = Calendar.getInstance();
//
//        if ((passwordResetToken.getExpirationTime().getTime()
//                - cal.getTime().getTime()) <= 0) {
//            passwordResetTokenRepository.delete(passwordResetToken);
//            return "expired";
//        }
//
//        return "valid";
//    }
//
//    public Optional<User> getUserByPasswordResetToken(String token) {
//        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
//    }
//
//    public void changePassword(User user, String newPassword) {
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//    }
//
//    public boolean checkIfValidOldPassword(User user, String oldPassword) {
//        return passwordEncoder.matches(oldPassword, user.getPassword());
//    }
}
