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
    @Size(min = 6, message = "비밀번호는 6자 이상 입력해주세요.")
    private String password;
}
