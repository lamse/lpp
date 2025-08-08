package hey.lpp.dto;

import hey.lpp.domain.user.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final String email;
    private final String name;

    public LoginResponseDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
