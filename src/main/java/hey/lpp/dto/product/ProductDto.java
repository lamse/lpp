package hey.lpp.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import hey.lpp.domain.product.Product;
import hey.lpp.domain.product.ProductImage;
import hey.lpp.dto.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private UserDto user;
    private String name;
    private String modelNo;
    private String url;
    private Integer price;
    private String description;
    private List<ProductImage> productImages;
    private boolean isRegistrant;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private int productOfferCount; // New field

    public ProductDto(Product product) {
        this.id = product.getId();
        this.user = new UserDto(product.getUser());
        this.name = product.getName();
        this.modelNo = product.getModelNo();
        this.url = product.getUrl();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.productImages = product.getProductImages();
        this.isRegistrant = product.isRegistrant();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.productOfferCount = product.getProductOffers() != null ? product.getProductOffers().size() : 0; // Initialize new field
    }
}
