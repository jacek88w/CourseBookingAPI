package pl.jacek.coursebookingresolving.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import pl.jacek.coursebookingresolving.entity.User;

@Setter
@Getter
public class RegistrationEvent extends ApplicationEvent {

    private User user;
    private String url;

    public RegistrationEvent(User user, String url) {
        super(user);
        this.user = user;
        this.url = url;
    }
}
