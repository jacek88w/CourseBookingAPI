package pl.jacek.coursebookingresolving.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jacek.coursebookingresolving.model.AuthenticationRequestModel;
import pl.jacek.coursebookingresolving.config.auth.AuthenticationResponse;
import pl.jacek.coursebookingresolving.config.auth.AuthenticationService;
import pl.jacek.coursebookingresolving.dto.UserDTO;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authenticationService.register(userDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequestModel request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
