package pl.jacek.coursebooking.excludetFromCompiling.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.jacek.coursebooking.entity.User;
import pl.jacek.coursebooking.excludetFromCompiling.event.RegistrationCompleteEvent;
import pl.jacek.coursebooking.service.UserService;

import java.util.UUID;

@Slf4j
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;

    public RegistrationCompleteEventListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //create verification token for user
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);
        //send mail
        String url =
                event.getApplicationUrl()
                        + "/verifyRegistration?token="
                        + token;
        log.info("click the link to verify your account: {}",
                url); //todo change this to a mail sender
    }
}
