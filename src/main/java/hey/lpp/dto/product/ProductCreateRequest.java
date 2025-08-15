package hey.lpp.dto.product;

import hey.lpp.domain.product.ValidFile;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductCreateRequest {

    @NotEmpty
    private String name;

    private String modelNo;

    @NotEmpty
    @URL
    private String url;

    @NotNull
    private Integer price;

    private String description;

    @ValidFile(allowFileDefines = {"jpeg", "png", "jpg"}, message = "Please select one or more image files.")
    private List<MultipartFile> imageFiles;
}
