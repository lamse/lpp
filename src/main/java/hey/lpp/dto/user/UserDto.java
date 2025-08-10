package hey.lpp.dto.user;

import hey.lpp.domain.user.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
