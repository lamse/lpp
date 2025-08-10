package hey.lpp.service.product;

import hey.lpp.domain.product.Product;
import hey.lpp.dto.product.ProductCreateRequest;
import hey.lpp.domain.product.ProductImage;
import hey.lpp.domain.product.UploadFile;
import hey.lpp.domain.user.User;
import hey.lpp.repository.product.ProductImageRepository;
import hey.lpp.repository.product.ProductRepository;
import hey.lpp.util.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final FileStore fileStore;

    @SneakyThrows
    public Product add(ProductCreateRequest productCreateRequest, User user) {
        Product product = new Product();
        product.setUser(user);
        product.setName(productCreateRequest.getName());
        product.setModelNo(productCreateRequest.getModelNo());
        product.setUrl(productCreateRequest.getUrl());
        product.setPrice(productCreateRequest.getPrice());
        product.setDescription(productCreateRequest.getDescription());
        Product saveProduct = productRepository.save(product);

        List<UploadFile> imageFiles = fileStore.storeFiles(productCreateRequest.getImageFiles());
        for (UploadFile imageFile : imageFiles) {
            ProductImage productImage = new ProductImage();
            productImage.setProductId(saveProduct.getId());
            productImage.setUploadName(imageFile.getUploadFileName());
            productImage.setStoreName(imageFile.getStoreFileName());
            productImageRepository.save(productImage);
        }

        return saveProduct;
    }

}
