package hey.lpp.controller.api.product;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.product.ProductOfferChat;
import hey.lpp.domain.user.User;
import hey.lpp.dto.ApiResponse;
import hey.lpp.dto.product.ProductOfferChatCreateRequest;
import hey.lpp.repository.product.ProductOfferChatRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/offer/chat")
public class ProductOfferChatApiController {
    private final ProductOfferChatRepository productOfferChatRepository;

    @PostMapping({"{productOfferId}"})
    public ResponseEntity<ApiResponse<?>> chat(@PathVariable Long productOfferId, @RequestBody ProductOfferChatCreateRequest request, Model model, HttpSession httpSession) {
        ProductOfferChat productOfferChat = new ProductOfferChat();
        User user = (User) httpSession.getAttribute(SessionConst.LOGIN_USER);
        productOfferChat.setUser(user);
        productOfferChat.setProductOfferId(productOfferId);
        productOfferChat.setComment(request.getComment());
        productOfferChatRepository.save(productOfferChat);

        return ResponseEntity.ok(ApiResponse.success(productOfferChat));
    }

    @ResponseBody
    @DeleteMapping("{chatID}/delete")
    public ResponseEntity<ApiResponse<?>> deleteChat(@PathVariable Long chatID, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(SessionConst.LOGIN_USER);
        log.info("Deleting chat with chatID: {}, userId: {}", chatID, user.getId());
        int deleted = productOfferChatRepository.deleteByIdAndUserId(chatID, user.getId());
        return ResponseEntity.ok(ApiResponse.success(deleted));
    }
}
