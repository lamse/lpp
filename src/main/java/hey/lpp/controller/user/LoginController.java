package hey.lpp.controller.user;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.user.LoginForm;
import hey.lpp.domain.user.User;
import hey.lpp.service.user.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(LoginForm loginForm, Model model) {
        model.addAttribute("loginForm", loginForm);
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {

        log.info("로그인 시도: {}", loginForm);

        if (bindingResult.hasErrors()) {
            return "redirect:/login"; // 에러가 있는 경우 다시 폼으로 이동
        }

        // 로그인 처리 로직
        User user = loginService.login(loginForm);
        if (user == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다."); // 로그인 실패 메시지 추가
            return "login/loginForm"; // 로그인 실패 시 다시 폼으로 이동
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER_ID, user.getId());

        return "redirect:/"; // 로그인 성공 시 홈으로 리다이렉트
    }

    @PostMapping("/logout")
    public String logout(@SessionAttribute(name = SessionConst.LOGIN_USER_ID, required = false) Long userId
            , HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (userId != null) {
            session.removeAttribute(SessionConst.LOGIN_USER_ID);
        }

        return "redirect:/login";
    }
}
