package hey.lpp.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JoinRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    @Size(min = 6, message = "Please enter a password of at least 6 characters..")
    private String password;
}
