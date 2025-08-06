package hey.lpp.controller.product;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.product.ProductOfferChat;
import hey.lpp.domain.user.User;
import hey.lpp.repository.product.ProductOfferChatRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product/offer/chat")
public class ProductOfferChatController {
    private final ProductOfferChatRepository productOfferChatRepository;

    @GetMapping("{productOfferId}")
    public String chatList(@PathVariable Long productOfferId, Model model) {
        List<ProductOfferChat> productOfferChats = productOfferChatRepository.findByProductOfferId(productOfferId);

        model.addAttribute("productOfferId", productOfferId);
        model.addAttribute("productOfferChats", productOfferChats);
        // 채팅 페이지로 이동
        return "product/offer/chat";
    }

    @PostMapping({"{productOfferId}"})
    public String chat(@PathVariable Long productOfferId, @Param("comment") String comment, Model model, HttpSession httpSession) {

        ProductOfferChat productOfferChat = new ProductOfferChat();
        User user = (User) httpSession.getAttribute(SessionConst.LOGIN_USER);
        productOfferChat.setUser(user);
        productOfferChat.setProductOfferId(productOfferId);
        productOfferChat.setComment(comment);
        productOfferChatRepository.save(productOfferChat);


        return "redirect:/product/offer/chat/" + productOfferId;
    }

    @ResponseBody
    @DeleteMapping("{chatID}/delete")
    public int deleteChat(@PathVariable Long chatID, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(SessionConst.LOGIN_USER);
        log.info("Deleting chat with chatID: {}, userId: {}", chatID, user.getId());
        return productOfferChatRepository.deleteByIdAndUserId(chatID, user.getId());
    }
}
