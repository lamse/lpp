package hey.lpp.domain.product;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ProductForm {

    @NotEmpty
    private String name;

    @ValidFile(allowFileDefines = {"jpeg", "png", "jpg"}, message = "이미지 파일은 필수입니다.")
    private MultipartFile imageFile;

    @NotEmpty
    @URL
    private String url;
}
