package hey.lpp.service.product;

import hey.lpp.domain.product.Product;
import hey.lpp.domain.product.ProductForm;
import hey.lpp.domain.user.User;
import hey.lpp.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    @SneakyThrows
    public Product add(ProductForm productForm, User user) {
        Product product = new Product();
        product.setUser(user);
        product.setName(productForm.getName());
        product.setImage("https://picsum.photos/200/300");
        product.setUrl(productForm.getUrl());
        log.info("ProductService.add() - product: {}", product);
        log.info("ProductService.add() - user: {}", user);
        return productRepository.save(product);
    }

}
