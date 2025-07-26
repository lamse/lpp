package hey.lpp.service.user;

import hey.lpp.domain.user.LoginForm;
import hey.lpp.domain.user.User;
import hey.lpp.repository.user.UserRepository;
import hey.lpp.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public List<User> users() {
        return List.of();
    }

    public User saveUser(User user) {
        return null;
    }

    public User updateUser(User user) {
        return null;
    }

    public void deleteUser(Long id) {

    }

}
