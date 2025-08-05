package hey.lpp.repository.product;

import hey.lpp.domain.product.ProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOfferRepository extends JpaRepository<ProductOffer, Long> {
}
