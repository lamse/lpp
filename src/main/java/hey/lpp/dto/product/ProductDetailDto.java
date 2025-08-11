package hey.lpp.dto.product;

import hey.lpp.domain.product.Product;
import hey.lpp.domain.product.ProductOffer;
import lombok.Data;
import java.util.List;

@Data
public class ProductDetailDto {
    private ProductDto product;
    private List<ProductOffer> productOffers;

    public ProductDetailDto(Product product) {
        this.product = new ProductDto(product);
        this.productOffers = product.getProductOffers();
    }
}