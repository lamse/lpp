package hey.lpp.controller;

import hey.lpp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
