package hey.lpp.controller.product;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.product.Product;
import hey.lpp.domain.product.ProductForm;
import hey.lpp.domain.user.User;
import hey.lpp.repository.user.UserRepository;
import hey.lpp.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final UserRepository userRepository;
    private final ProductService productService;

    @GetMapping("/add")
    public String addProductForm(@SessionAttribute(name = SessionConst.LOGIN_USER_ID, required = false) Long userId,
                       Model model, HttpServletRequest request) {
        if (userId == null) {
            return "redirect:/login";
        }


        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> model.addAttribute("user", value));

        model.addAttribute("referer", request.getHeader("Referer"));
        model.addAttribute("productForm", new ProductForm());
        return "product/addProductForm";
    }

    @PostMapping("/add")
    public String addProduct(@SessionAttribute(name = SessionConst.LOGIN_USER_ID, required = false) Long userId,
                             @Validated ProductForm productForm, BindingResult bindingResult) {

        if (userId == null) {
            return "redirect:/login";
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "product/addProductForm";
        }

        productService.add(productForm, user.get());

        return "redirect:/";
    }
}
