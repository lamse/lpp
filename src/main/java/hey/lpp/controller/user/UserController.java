package hey.lpp.controller.user;

import hey.lpp.domain.user.JoinForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/join")
    public String join(JoinForm joinForm) {
        return "user/joinForm";
    }
}
