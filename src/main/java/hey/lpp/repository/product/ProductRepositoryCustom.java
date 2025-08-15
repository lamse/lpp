package hey.lpp.repository.product;

import hey.lpp.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Product> search(String name, String modelNo, Integer minPrice, Integer maxPrice, Pageable pageable);
}
