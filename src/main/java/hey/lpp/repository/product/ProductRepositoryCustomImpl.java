package hey.lpp.repository.product;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.lpp.domain.product.Product;
import hey.lpp.domain.product.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> search(String name, String modelNo, Integer minPrice, Integer maxPrice, Pageable pageable) {
        QProduct product = QProduct.product;

        List<Product> products = queryFactory
                .selectFrom(product)
                .where(
                        likeName(name),
                        likeModelNo(modelNo),
                        betweenPrice(minPrice, maxPrice)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(product)
                .where(
                        likeName(name),
                        likeModelNo(modelNo),
                        betweenPrice(minPrice, maxPrice)
                )
                .fetchCount();

        return new PageImpl<>(products, pageable, total);
    }

    private BooleanExpression likeName(String name) {
        if (StringUtils.hasText(name)) {
            return QProduct.product.name.like("%" + name + "%");
        }
        return null;
    }

    private BooleanExpression likeModelNo(String modelNo) {
        if (StringUtils.hasText(modelNo)) {
            return QProduct.product.modelNo.like("%" + modelNo + "%");
        }
        return null;
    }

    private BooleanExpression betweenPrice(Integer minPrice, Integer maxPrice) {
        if (minPrice != null && maxPrice != null) {
            return QProduct.product.price.between(minPrice, maxPrice);
        }
        if (minPrice != null) {
            return QProduct.product.price.goe(minPrice);
        }
        if (maxPrice != null) {
            return QProduct.product.price.loe(maxPrice);
        }
        return null;
    }
}
