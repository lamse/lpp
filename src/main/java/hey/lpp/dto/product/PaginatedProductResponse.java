package hey.lpp.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedProductResponse {
    private List<ProductDto> products;
    private boolean first;
    private int number;
    private int size;
    private int totalPages;
    private boolean last;
    private long totalElements;
}
