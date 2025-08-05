package hey.lpp.aop;

import hey.lpp.Constant.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Slf4j
@Aspect
@Component
public class LoginCheckAspect {
    @Around("hey.lpp.aop.Pointcuts.allProduct() && !hey.lpp.aop.Pointcuts.viewProduct()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            String targetUri = request.getRequestURI();
            if (request.getMethod().equals("POST")) {
                targetUri = "/";
                // POST 요청인 경우, Referer 헤더를 사용하여 이전 페이지로 돌아갈 수 있도록 함
                String referer = request.getHeader("Referer");
                if (referer != null) {
                    targetUri = referer;
                }
            }

            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/login?redirectURL=" + targetUri;
        }
        return joinPoint.proceed();
    }
}
