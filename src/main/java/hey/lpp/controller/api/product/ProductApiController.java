package hey.lpp.controller.api.product;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.product.Product;
import hey.lpp.dto.product.ProductCreateRequest;
import hey.lpp.domain.user.User;
import hey.lpp.dto.ApiResponse;
import hey.lpp.dto.product.ProductCreateResponse;
import hey.lpp.dto.product.ProductDto;
import hey.lpp.repository.product.ProductRepository;
import hey.lpp.service.product.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import hey.lpp.dto.product.PaginatedResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductApiController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> viewProduct(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.<ResponseEntity<ApiResponse<?>>>map(value ->
                ResponseEntity.ok(ApiResponse.success(new ProductDto(value))))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(ApiResponse.error(Map.of("global", "상품을 찾을 수 없습니다.")))
                );
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<?>> addProduct(
            @Validated ProductCreateRequest productCreateRequest,
            HttpSession httpSession
    ) {
        log.info("add product");
        User user = (User) httpSession.getAttribute(SessionConst.LOGIN_USER);

        Product product = productService.add(productCreateRequest, user);
        log.info("상품 추가 완료: {}", product);
        return ResponseEntity.ok(ApiResponse.success(new ProductCreateResponse(product.getId())));
    }

        @GetMapping
        public ResponseEntity<ApiResponse<PaginatedResponse<ProductDto>>> getProducts(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "8") int size
        ) {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
            Page<Product> productPage = productService.findAll(pageable);
            List<ProductDto> productDtos = productPage.getContent().stream()
                    .map(ProductDto::new)
                    .collect(Collectors.toList());

            PaginatedResponse<ProductDto> paginatedProductResponse = new PaginatedResponse<>(
                    productDtos,
                    productPage.isFirst(),
                    productPage.getNumber(),
                    productPage.getSize(),
                    productPage.getTotalPages(),
                    productPage.isLast(),
                    productPage.getTotalElements()
            );
            return ResponseEntity.ok(ApiResponse.success(paginatedProductResponse));
        }
}
