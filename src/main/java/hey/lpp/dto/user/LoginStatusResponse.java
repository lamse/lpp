package hey.lpp.dto.user;

import hey.lpp.domain.user.User;
import lombok.Data;

@Data
public class LoginStatusResponse {
    private boolean loggedIn;
    private UserDto user;

    public LoginStatusResponse(boolean loggedIn, User user) {
        this.loggedIn = loggedIn;
        if (user != null) {
            this.user = new UserDto(user);
        }
    }
}
