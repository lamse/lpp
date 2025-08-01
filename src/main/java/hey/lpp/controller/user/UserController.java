package hey.lpp.controller.user;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.user.User;
import hey.lpp.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public String join(Model model, HttpServletRequest request) {
        model.addAttribute("referer", request.getHeader("Referer"));
        model.addAttribute("user", new User());
        return "user/joinForm";
    }

    @PostMapping("/join")
    public String join(@Validated User user, BindingResult bindingResult, HttpSession httpSession) {

        log.info("가입 시도: {}", user);

        if (bindingResult.hasErrors()) {
            return "redirect:/login"; // 에러가 있는 경우 다시 폼으로 이동
        }

        // 로그인 처리 로직
        try {
            User joinUser = userService.join(user);
            log.info("회원 가입 성공: {}", joinUser);
            joinUser.setPassword(null);
            httpSession.setAttribute(SessionConst.LOGIN_USER, joinUser);
        } catch (Exception ex) {
            log.error("회원 가입 실패: {}", ex.getMessage());
            bindingResult.reject("joinFail", "이미 존재하는 이메일입니다."); // 가입 실패 메시지 추가
            return "user/joinForm"; // 가입 실패 시 다시 폼으로 이동
        }

        return "redirect:/";
    }
}
