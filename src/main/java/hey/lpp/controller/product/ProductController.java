package hey.lpp.controller.product;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.product.ProductForm;
import hey.lpp.domain.user.User;
import hey.lpp.repository.user.UserRepository;
import hey.lpp.service.product.ProductService;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final UserRepository userRepository;
    private final ProductService productService;

    @GetMapping("/add")
    public String addProductForm(Model model, HttpServletRequest request) {
        model.addAttribute("referer", request.getHeader("Referer"));
        model.addAttribute("productForm", new ProductForm());
        return "product/addProductForm";
    }

    @PostMapping("/add")
    public String addProduct(@Validated ProductForm productForm, BindingResult bindingResult, HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            return "product/addProductForm";
        }
        User user = (User) httpSession.getAttribute(SessionConst.LOGIN_USER);
        productService.add(productForm, user);

        return "redirect:/";
    }
}
