package hey.lpp.controller.api.user;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.user.User;
import hey.lpp.dto.ApiResponse;
import hey.lpp.dto.user.JoinRequest;
import hey.lpp.service.user.UserService;
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
public class UserApiController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<?>> join(@Validated @RequestBody JoinRequest joinRequest, BindingResult bindingResult, HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            log.info("가입 폼 유효성 검사 실패: {}", bindingResult.getAllErrors());
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "")
                    );
            return ResponseEntity.badRequest().body(ApiResponse.error(errors));
        }

        // 회원 가입 처리 로직
        try {
            User joinUser = userService.join(joinRequest);
            log.info("회원 가입 성공: {}", joinUser);
            joinUser.setPassword(null);
            httpSession.setAttribute(SessionConst.LOGIN_USER, joinUser);

            return ResponseEntity.ok(ApiResponse.success(joinUser));
        } catch (Exception ex) {
            log.error("회원 가입 실패: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(Map.of("global", "This email already exists.")));
        }

    }
}
