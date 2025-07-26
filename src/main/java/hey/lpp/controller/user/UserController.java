package hey.lpp.controller.user;

import hey.lpp.domain.user.LoginForm;
import hey.lpp.domain.user.User;
import hey.lpp.service.user.LoginService;
import hey.lpp.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(LoginForm loginForm) {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated LoginForm form, BindingResult bindingResult, HttpServletRequest request) {

        log.info("로그인 시도: {}", form);
        log.info("BindingResult: {}", bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/loginForm"; // 에러가 있는 경우 다시 폼으로 이동
        }

        // 로그인 처리 로직
        User user = loginService.login(form);
        if (user == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다."); // 로그인 실패 메시지 추가
            return "user/loginForm"; // 로그인 실패 시 다시 폼으로 이동
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginUserId", user.getId());

        return "redirect:/"; // 로그인 성공 시 홈으로 리다이렉트
    }
}
