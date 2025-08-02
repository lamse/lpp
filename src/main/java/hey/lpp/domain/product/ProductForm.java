package hey.lpp.domain.product;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductForm {

    @NotEmpty
    private String name;

    @ValidFile(allowFileDefines = {"jpeg", "png", "jpg"}, message = "이미지 파일을 1개 이상 선택해 주세요.")
    private List<MultipartFile> imageFiles;

    @NotEmpty
    @URL
    private String url;
}
