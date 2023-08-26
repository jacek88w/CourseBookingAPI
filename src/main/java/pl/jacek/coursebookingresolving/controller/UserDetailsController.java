package pl.jacek.coursebookingresolving.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.entity.VerificationToken;
import pl.jacek.coursebookingresolving.model.AuthenticationRequestModel;
import pl.jacek.coursebookingresolving.model.AuthenticationResponseModel;
import pl.jacek.coursebookingresolving.model.PasswordModel;
import pl.jacek.coursebookingresolving.model.RegisterRequestModel;
import pl.jacek.coursebookingresolving.service.AuthenticationService;
import pl.jacek.coursebookingresolving.service.JwtService;
import pl.jacek.coursebookingresolving.service.UserService;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserDetailsController {

    private final UserService userService;
    private final JwtService jwtService;

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
        jwtService.resendVerificationTokenMail(user, authenticationService.applicationUrl(request), verificationToken);
        return "Verification Link Send";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseModel> register(@RequestBody RegisterRequestModel registerRequest, HttpServletRequest servletRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest, servletRequest));
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
            url = passwordResetTokenMail(user, authenticationService.applicationUrl(request), token);
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



    @GetMapping("/hello")
    public String hello() {
        return "Hello everyone!";
    }


}
