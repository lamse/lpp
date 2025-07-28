package hey.lpp.controller.product;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.product.Product;
import hey.lpp.domain.user.User;
import hey.lpp.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {
    private final UserRepository userRepository;

    @GetMapping("/product")
    public String join(@SessionAttribute(name = SessionConst.LOGIN_USER_ID, required = false) Long userId,
                       Model model, HttpServletRequest request) {
        if (userId == null) {
            return "redirect:/login";
        }


        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> model.addAttribute("user", value));

        model.addAttribute("referer", request.getHeader("Referer"));
        model.addAttribute("product", new Product());
        return "product/addProductForm";
    }
}
