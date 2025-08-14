package hey.lpp.controller.api.product;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.product.Product;
import hey.lpp.domain.product.ProductOffer;
import hey.lpp.domain.user.User;
import hey.lpp.dto.ApiResponse;
import hey.lpp.dto.product.ProductOfferCreateRequest;
import hey.lpp.repository.product.ProductOfferRepository;
import hey.lpp.repository.product.ProductRepository;
import hey.lpp.service.product.ProductOfferService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductOfferApiController {
    private final ProductRepository productRepository;
    private final ProductOfferService productOfferService;
    private final ProductOfferRepository productOfferRepository;

    @PostMapping("/{id}/offer")
    public ResponseEntity<ApiResponse<?>> productOffer(
            @PathVariable Long id,
            @Validated @RequestBody ProductOfferCreateRequest productOfferCreateRequest,
            BindingResult bindingResult,
            HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            log.info("로그인 폼 유효성 검사 실패: {}", bindingResult.getAllErrors());
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "")
                    );
            return ResponseEntity.badRequest().body(ApiResponse.error(errors));
        }

        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(Map.of("global", "The product could not be found.")));
        }

        ProductOffer productOffer = productOfferService.save(id, productOfferCreateRequest, httpSession);

        return ResponseEntity.ok(ApiResponse.success(productOffer));
    }

    @PostMapping("/{id}/offer/choose/{offerId}")
    public ResponseEntity<ApiResponse<?>> chooseOffer(@PathVariable Long id, @PathVariable Long offerId, HttpSession httpSession) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(Map.of("global", "The product could not be found.")));
        }

        Optional<ProductOffer> offer = productOfferRepository.findById(offerId);
        if (offer.isEmpty() || !offer.get().getProductId().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(Map.of("global", "No offers found.")));
        }

        User user = (User) httpSession.getAttribute(SessionConst.LOGIN_USER);
        if (user == null || !user.getId().equals(product.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(Map.of("global", "You do not have permission.")));
        }

        ProductOffer productOffer = productOfferService.setChoose(product.get(), offer.get());

        return ResponseEntity.ok(ApiResponse.success(productOffer));
    }
}
