package hey.lpp.dto.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
public class ProductOfferCreateRequest {

    @NotEmpty
    @URL
    private String url;

    @NotNull
    private Integer price;
}
