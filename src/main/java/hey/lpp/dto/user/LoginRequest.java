package hey.lpp.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 4, message = "비밀번호는 4자 이상 입력해주세요.")
    private String password;
}
