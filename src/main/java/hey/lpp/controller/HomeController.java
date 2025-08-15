package hey.lpp.controller;

import hey.lpp.domain.product.Product;
import hey.lpp.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductRepository productRepository;

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "8") int size,
                       Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Product> productPage = productRepository.findAll(pageable);
        model.addAttribute("productPage", productPage);

        return "home";
    }
}
