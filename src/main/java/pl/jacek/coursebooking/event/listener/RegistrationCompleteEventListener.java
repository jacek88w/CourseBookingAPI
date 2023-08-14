package pl.jacek.coursebooking.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.jacek.coursebooking.entity.User;
import pl.jacek.coursebooking.event.RegistrationCompleteEvent;
import pl.jacek.coursebooking.service.UserService;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

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
