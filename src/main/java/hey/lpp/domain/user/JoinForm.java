package hey.lpp.domain.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class JoinForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;
}
