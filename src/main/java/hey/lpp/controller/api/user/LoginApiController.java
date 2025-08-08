package hey.lpp.controller.api.user;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.user.LoginForm;
import hey.lpp.domain.user.User;
import hey.lpp.dto.ApiResponse;
import hey.lpp.dto.LoginResponseDto;
import hey.lpp.service.user.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginApiController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(
            @Validated @RequestBody LoginForm loginForm,
            BindingResult bindingResult,
            HttpSession session) {

        if (bindingResult.hasErrors()) {
            log.info("로그인 폼 유효성 검사 실패: {}", bindingResult.getAllErrors());
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "")
                    );
            return ResponseEntity.badRequest().body(ApiResponse.error(errors));
        }

        // 로그인 처리 로직
        User user = loginService.login(loginForm);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(Map.of("global", "아이디 또는 비밀번호가 일치하지 않습니다.")));
        }

        user.setPassword(null); // 보안을 위해 세션에 저장하기 전에 비밀번호를 제거
        session.setAttribute(SessionConst.LOGIN_USER, user);

        return ResponseEntity.ok(ApiResponse.success(new LoginResponseDto(user)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        // stateless 한 api 에서는 보통 클라이언트에서 토큰을 삭제하는 방식으로 로그아웃을 처리합니다.
        // 서버에서는 특별한 처리가 필요 없을 수 있습니다.
        return ResponseEntity.ok(ApiResponse.success("로그아웃 되었습니다."));
    }
}
