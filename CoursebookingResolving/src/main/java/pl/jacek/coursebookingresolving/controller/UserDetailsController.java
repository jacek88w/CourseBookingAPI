package pl.jacek.coursebooking.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jacek.coursebooking.model.AuthenticationRequestModel;
import pl.jacek.coursebooking.model.AuthenticationResponseModel;
import pl.jacek.coursebooking.service.AuthenticationService;
import pl.jacek.coursebooking.model.RegisterRequestModel;
import pl.jacek.coursebooking.entity.User;
import pl.jacek.coursebooking.entity.VerificationToken;
import pl.jacek.coursebooking.model.PasswordModel;
import pl.jacek.coursebooking.service.UserService;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserDetailsController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

//    private final ApplicationEventPublisher publisher;

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")) {
            return "User Verifies Successfully";
        }
        return "Bad user";
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          HttpServletRequest request) {
        VerificationToken verificationToken
                = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification Link Send";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseModel> register(@RequestBody RegisterRequestModel request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseModel> register(@RequestBody AuthenticationRequestModel request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if(user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, applicationUrl(request), token);
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordModel passwordModel) {
        String result = userService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")) {
            return "invalid Token";
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()) {
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password Reset Successfully";
        } else {
            return "invalid Token";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        if(!userService.checkIfValidOldPassword(user, passwordModel.getOldPassword())) {
            return "Invalid Old Password";
        }

        //Save new password
        userService.changePassword(user, passwordModel.getNewPassword());
        return "Password Changed Successfully!";
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url =
                applicationUrl
                        + "/savePassword?token="
                        + token;

        log.info("click the link to reset your Password: {}",
                url); //todo change this to a mail sender
        return url;
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url =
                applicationUrl
                        + "/verifyRegistration?token="
                        + verificationToken.getToken();

        log.info("click the link to verify your account: {}",
                url); //todo change this to a mail sender
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello everyone!";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() + ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
