package hey.lpp.service.user;

import hey.lpp.domain.user.User;
import hey.lpp.repository.user.UserRepository;
import hey.lpp.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @SneakyThrows
    public User join(User user) {
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

}
