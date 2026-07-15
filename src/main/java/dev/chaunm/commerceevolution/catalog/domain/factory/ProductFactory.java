package dev.chaunm.commerceevolution.catalog.domain.factory;

import dev.chaunm.commerceevolution.catalog.domain.event.ProductCreatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.ProductDuplicatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.ProductMedia;
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
                null,
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

    // Variants are not copied: SKUs are globally unique, so cloning them verbatim would collide.
    public static Product duplicate(Product source, ProductName name, Slug slug) {
        List<ProductMedia> medias = source.getMedias().stream()
                .map(media -> new ProductMedia(
                        MediaId.generate(),
                        media.getUrl(),
                        media.getSortOrder(),
                        media.isPrimary()
                ))
                .toList();

        Product duplicate = new Product(
                ProductId.generate(),
                name,
                slug,
                source.getCategoryId(),
                source.getBrandId(),
                ProductStatus.DRAFT,
                null,
                List.of(),
                medias
        );

        duplicate.registerEvent(new ProductDuplicatedEvent(duplicate.getId(), source.getId()));

        return duplicate;
    }
}
