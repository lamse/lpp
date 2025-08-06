package hey.lpp.repository.product;

import hey.lpp.domain.product.ProductOfferChat;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOfferChatRepository extends JpaRepository<ProductOfferChat, Long> {
    List<ProductOfferChat> findByProductOfferId(Long productOfferId);
    @Transactional
    //int deleteByIdAndUserId(Long id, Long userId);
    @Modifying
    @Query("delete from ProductOfferChat c where c.id = :id and c.user.id = :userId")
    int deleteByIdAndUserId(Long id, Long userId);

}
