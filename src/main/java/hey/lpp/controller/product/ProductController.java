package hey.lpp.controller.product;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.YesNo;
import hey.lpp.domain.product.Product;
import hey.lpp.domain.product.ProductForm;
import hey.lpp.domain.product.ProductOffer;
import hey.lpp.domain.product.ProductOfferForm;
import hey.lpp.domain.user.User;
import hey.lpp.repository.product.ProductOfferRepository;
import hey.lpp.repository.product.ProductRepository;
import hey.lpp.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ProductOfferRepository productOfferRepository;

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

    @GetMapping("{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return "error/404"; // 상품이 존재하지 않을 경우 404 페이지로 이동
        }

        model.addAttribute("product", product.get());
        return "product/viewProduct";
    }

    @PostMapping("/{id}/offer")
    public String productOffer(@PathVariable Long id, @Validated ProductOfferForm productOfferForm) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return "error/404"; // 상품이 존재하지 않을 경우 404 페이지로 이동
        }

        ProductOffer productOffer = new ProductOffer();
        productOffer.setProductId(id);
        productOffer.setUrl(productOfferForm.getUrl());
        productOffer.setPrice(productOfferForm.getPrice());
        productOffer.setChoose(YesNo.N);

        productOfferRepository.save(productOffer);

        return "redirect:/product/" + id; // 상품 상세 페이지로 리다이렉트
    }
}
