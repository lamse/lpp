package hey.lpp.service.product;

import hey.lpp.Constant.SessionConst;
import hey.lpp.domain.YesNo;
import hey.lpp.domain.product.Product;
import hey.lpp.domain.product.ProductOffer;
import hey.lpp.domain.user.User;
import hey.lpp.dto.product.ProductOfferCreateRequest;
import hey.lpp.repository.product.ProductOfferRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductOfferService {
    private final ProductOfferRepository productOfferRepository;

    public ProductOffer save(Long productId, ProductOfferCreateRequest productOfferCreateRequest, HttpSession httpSession) {
        ProductOffer productOffer = new ProductOffer();
        User user = (User) httpSession.getAttribute(SessionConst.LOGIN_USER);
        productOffer.setProductId(productId);
        productOffer.setUser(user);
        productOffer.setUrl(productOfferCreateRequest.getUrl());
        productOffer.setPrice(productOfferCreateRequest.getPrice());
        productOffer.setChoose(YesNo.N);

        return productOfferRepository.save(productOffer);
    }

    public ProductOffer setChoose(Product product, ProductOffer offer) {
        product.getProductOffers().stream().filter(
                po -> po.getChoose() == YesNo.Y
        ).forEach(po -> {
            po.setChoose(YesNo.N); // 기존 선택된 제안은 선택 해제
            productOfferRepository.save(po);
        });

        offer.setChoose(YesNo.Y);
        return productOfferRepository.save(offer);
    }
}
