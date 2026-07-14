package dev.chaunm.commerceevolution.catalog.domain.model;

import dev.chaunm.commerceevolution.catalog.domain.event.ProductArchivedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.ProductPublishedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.ProductUpdatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.MediaAddedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.VariantAddedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.VariantRemovedEvent;
import dev.chaunm.commerceevolution.catalog.domain.exception.DuplicateVariantSkuException;
import dev.chaunm.commerceevolution.catalog.domain.exception.InvalidMediaUrlException;
import dev.chaunm.commerceevolution.catalog.domain.exception.InvalidProductStatusTransitionException;
import dev.chaunm.commerceevolution.catalog.domain.exception.ProductArchivedException;
import dev.chaunm.commerceevolution.catalog.domain.exception.VariantNotFoundException;
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

    public void publish() {
        if (this.status != ProductStatus.DRAFT) {
            throw new InvalidProductStatusTransitionException(this.status, ProductStatus.PUBLISHED);
        }
        this.status = ProductStatus.PUBLISHED;
        registerEvent(new ProductPublishedEvent(this.id));
    }

    public void archive() {
        if (this.status == ProductStatus.ARCHIVED) {
            throw new InvalidProductStatusTransitionException(this.status, ProductStatus.ARCHIVED);
        }
        this.status = ProductStatus.ARCHIVED;
        registerEvent(new ProductArchivedEvent(this.id));
    }

    public ProductVariant addVariant(SKU sku, String name) {
        if (this.status == ProductStatus.ARCHIVED) {
            throw new ProductArchivedException();
        }
        boolean duplicateInProduct = variants.stream()
                .anyMatch(variant -> variant.getSku().equals(sku));
        if (duplicateInProduct) {
            throw new DuplicateVariantSkuException(sku);
        }

        ProductVariant variant = new ProductVariant(VariantId.generate(), sku, name, true);
        variants.add(variant);
        registerEvent(new VariantAddedEvent(this.id, variant.getId(), variant.getSku()));

        return variant;
    }

    public void removeVariant(VariantId variantId) {
        if (this.status == ProductStatus.ARCHIVED) {
            throw new ProductArchivedException();
        }

        ProductVariant variant = variants.stream()
                .filter(v -> v.getId().equals(variantId))
                .findFirst()
                .orElseThrow(VariantNotFoundException::new);

        variants.remove(variant);
        registerEvent(new VariantRemovedEvent(this.id, variantId));
    }

    public ProductMedia addMedia(String url, boolean primary) {
        if (this.status == ProductStatus.ARCHIVED) {
            throw new ProductArchivedException();
        }
        if (url == null || url.isBlank()) {
            throw new InvalidMediaUrlException(url);
        }

        if (primary) {
            medias.forEach(ProductMedia::unmarkPrimary);
        }

        ProductMedia media = new ProductMedia(MediaId.generate(), url, medias.size(), primary);
        medias.add(media);
        registerEvent(new MediaAddedEvent(this.id, media.getId(), media.getUrl()));

        return media;
    }

    public List<ProductVariant> getVariants() {
        return Collections.unmodifiableList(variants);
    }

    public List<ProductMedia> getMedias() {
        return Collections.unmodifiableList(medias);
    }
}
