package hey.lpp.service.user;

import hey.lpp.domain.user.LoginForm;
import hey.lpp.domain.user.User;
import hey.lpp.repository.user.UserRepository;
import hey.lpp.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final UserRepository userRepository;

    @SneakyThrows
    public User login (LoginForm form) {
        User user = userRepository.findByEmail((form.getEmail()))
                .orElse(null);
        if (user == null) {
            return null;
        }
        String hashedPassword = PasswordUtil.hashPassword(form.getPassword());
//        log.info("user password: {}", user.getPassword());
//        log.info("hashed password: {}", hashedPassword);

        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        return null;
    }
}
