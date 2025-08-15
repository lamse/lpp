package hey.lpp.controller.product;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.product.Product;
import hey.lpp.dto.product.ProductCreateRequest;
import hey.lpp.domain.product.ProductOffer;
import hey.lpp.dto.product.ProductOfferCreateRequest;
import hey.lpp.domain.user.User;
import hey.lpp.repository.product.ProductOfferRepository;
import hey.lpp.repository.product.ProductRepository;
import hey.lpp.service.product.ProductOfferService;
import hey.lpp.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final ProductOfferService productOfferService;


    @GetMapping("/search")
    public String searchProducts(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String modelNo,
                                 @RequestParam(required = false) Integer minPrice,
                                 @RequestParam(required = false) Integer maxPrice,
                                 @PageableDefault(size = 10) Pageable pageable,
                                 Model model) {
        Page<Product> productPage = productService.search(name, modelNo, minPrice, maxPrice, pageable);
        model.addAttribute("productPage", productPage);
        return "home";
    }

    @GetMapping("/add")
    public String addProductForm(Model model, HttpServletRequest request) {
        model.addAttribute("referer", request.getHeader("Referer"));
        model.addAttribute("productCreateRequest", new ProductCreateRequest());
        return "product/addProductForm";
    }

    @PostMapping("/add")
    public String addProduct(@Validated ProductCreateRequest productCreateRequest, BindingResult bindingResult, HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            return "product/addProductForm";
        }
        User user = (User) httpSession.getAttribute(SessionConst.LOGIN_USER);
        productService.add(productCreateRequest, user);

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
    public String productOffer(@PathVariable Long id, @Validated ProductOfferCreateRequest productOfferCreateRequest, HttpSession httpSession) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return "error/404"; // 상품이 존재하지 않을 경우 404 페이지로 이동
        }

        ProductOffer productOffer = productOfferService.save(id, productOfferCreateRequest, httpSession);

        return "redirect:/product/" + id; // 상품 상세 페이지로 리다이렉트
    }

    @PostMapping("/{id}/offer/choose/{offerId}")
    public String chooseOffer(@PathVariable Long id, @PathVariable Long offerId, HttpSession httpSession) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return "error/404"; // 상품이 존재하지 않을 경우 404 페이지로 이동
        }

        Optional<ProductOffer> offer = productOfferRepository.findById(offerId);
        if (offer.isEmpty() || !offer.get().getProductId().equals(id)) {
            return "error/404"; // 제안이 존재하지 않거나 상품과 일치하지 않는 경우 404 페이지로 이동
        }

        User user = (User) httpSession.getAttribute(SessionConst.LOGIN_USER);
        if (user == null || !user.getId().equals(product.get().getUser().getId())) {
            return "error/403"; // 권한이 없는 경우 403 페이지로 이동
        }

        productOfferService.setChoose(product.get(), offer.get());

        return "redirect:/product/" + id; // 상품 상세 페이지로 리다이렉트
    }
}
