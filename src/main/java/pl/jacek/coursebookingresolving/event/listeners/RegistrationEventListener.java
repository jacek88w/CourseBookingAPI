package pl.jacek.coursebookingresolving.event.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.event.RegistrationEvent;
import pl.jacek.coursebookingresolving.service.VerificationTokenService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {

    private final VerificationTokenService verificationTokenService;

    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.saveVerificationToken(user, token);

        String url = event.getUrl()
                + "/api/v1/auth/verifyRegistration?token="
                + token;

        log.info("Click the link to verify your account: {}", url);
    }
}
