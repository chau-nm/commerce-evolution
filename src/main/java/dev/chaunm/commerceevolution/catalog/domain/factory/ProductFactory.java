package dev.chaunm.commerceevolution.catalog.domain.factory;

import dev.chaunm.commerceevolution.catalog.domain.event.ProductCreatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.*;

import java.util.List;

public class ProductFactory {

    public static Product create(
            ProductName name,
            Slug slug,
            CategoryId categoryId,
            BrandId brandId
    ) {
        Product product = new Product(
                ProductId.generate(),
                name,
                slug,
                categoryId,
                brandId,
                ProductStatus.DRAFT,
                List.of(),
                List.of()
        );

        product.registerEvent(new ProductCreatedEvent(
                product.getId(),
                product.getName(),
                product.getSlug()
        ));

        return product;
    }
}
