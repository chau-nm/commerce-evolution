package dev.chaunm.commerceevolution.catalog.domain.model;

import dev.chaunm.commerceevolution.catalog.domain.event.ProductUpdatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.*;
import dev.chaunm.commerceevolution.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Product extends AggregateRoot {

    @Getter
    private final ProductId id;
    @Getter
    private ProductName name;
    @Getter
    private Slug slug;
    @Getter
    private CategoryId categoryId;
    @Getter
    private BrandId brandId;
    @Getter
    private ProductStatus status;
    private final List<ProductVariant> variants;
    private final List<ProductMedia> medias;

    public Product(
            ProductId id,
            ProductName name,
            Slug slug,
            CategoryId categoryId,
            BrandId brandId,
            ProductStatus status,
            List<ProductVariant> variants,
            List<ProductMedia> medias
    ) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.status = status;
        this.variants = new ArrayList<>(variants);
        this.medias = new ArrayList<>(medias);
    }

    public void updateDetails(ProductName name, Slug slug) {
        this.name = name;
        this.slug = slug;
        registerEvent(new ProductUpdatedEvent(this.id, this.name, this.slug));
    }

    public List<ProductVariant> getVariants() {
        return Collections.unmodifiableList(variants);
    }

    public List<ProductMedia> getMedias() {
        return Collections.unmodifiableList(medias);
    }
}
