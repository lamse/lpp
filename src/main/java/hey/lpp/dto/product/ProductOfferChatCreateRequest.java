package hey.lpp.dto.product;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProductOfferChatCreateRequest {

    @NotEmpty
    private String comment;
}
