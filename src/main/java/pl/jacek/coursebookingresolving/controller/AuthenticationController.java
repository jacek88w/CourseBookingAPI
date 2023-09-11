package pl.jacek.coursebookingresolving.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jacek.coursebookingresolving.dto.UserDTO;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.entity.VerificationToken;
import pl.jacek.coursebookingresolving.event.RegistrationEvent;
import pl.jacek.coursebookingresolving.service.UserService;
import pl.jacek.coursebookingresolving.service.VerificationTokenService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO, HttpServletRequest http) {
        var user = userService.register(userDTO);
        publisher.publishEvent(new RegistrationEvent(
                user,
                applicationUrl(http)
        ));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        return  verificationTokenService.validateUserWithVerificationToken(token);
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken
                = verificationTokenService.generateNewVerificationToken(oldToken);
        verificationTokenService.resendVerificationToken(applicationUrl(request), verificationToken);
        return "Verification link sent";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();

    }
}
