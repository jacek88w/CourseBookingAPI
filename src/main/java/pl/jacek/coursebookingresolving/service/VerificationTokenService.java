package pl.jacek.coursebookingresolving.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.entity.VerificationToken;
import pl.jacek.coursebookingresolving.repository.UserRepository;
import pl.jacek.coursebookingresolving.repository.VerificationTokenRepository;

import java.util.Calendar;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public void saveVerificationToken(User user, String token) {
        VerificationToken verificationToken
                = new VerificationToken(user, token);

        verificationTokenRepository.save(verificationToken);
    }

    public String validateUserWithVerificationToken(String token) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "invalid";
        }

        User user = verificationToken.getUser();


        if (isExpired(verificationToken)) {
            verificationTokenRepository.delete(verificationToken);
            return "Token is expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "User is verified";
    }

    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public void resendVerificationToken(String applicationUrl, VerificationToken verificationToken) {
        String url =
                applicationUrl
                        + "/verifyRegistration?token="
                        + verificationToken.getToken();

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);
    }

    public boolean isExpired(VerificationToken token) {
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime()
                - calendar.getTime().getTime()) <= 0) {
            return true;
        }
        return false;
    }
}
