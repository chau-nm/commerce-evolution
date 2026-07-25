package dev.chaunm.commerceevolution.catalog.domain.model.product;

import dev.chaunm.commerceevolution.catalog.domain.event.product.ProductArchivedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.product.ProductDeletedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.product.ProductPublishedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.product.ProductRestoredEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.product.ProductUpdatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.product.BrandChangedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.product.BrandUnassignedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.product.CategoryAssignedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.product.CategoryUnassignedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.media.MediaAddedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.media.MediaRemovedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.media.MediaReorderedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.media.MediaUpdatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.media.ThumbnailChangedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.variant.VariantAddedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.variant.VariantDisabledEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.variant.VariantEnabledEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.variant.VariantPriceChangedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.variant.VariantRemovedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.variant.VariantUpdatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.exception.variant.DuplicateVariantSkuException;
import dev.chaunm.commerceevolution.catalog.domain.exception.media.InvalidMediaOrderException;
import dev.chaunm.commerceevolution.catalog.domain.exception.media.InvalidMediaUrlException;
import dev.chaunm.commerceevolution.catalog.domain.exception.product.InvalidProductStatusTransitionException;
import dev.chaunm.commerceevolution.catalog.domain.exception.media.MediaAlreadyPrimaryException;
import dev.chaunm.commerceevolution.catalog.domain.exception.media.MediaNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductAlreadyDeletedException;
import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductArchivedException;
import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductDeletedException;
import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotDeletedException;
import dev.chaunm.commerceevolution.catalog.domain.exception.variant.VariantAlreadyActiveException;
import dev.chaunm.commerceevolution.catalog.domain.exception.variant.VariantAlreadyInactiveException;
import dev.chaunm.commerceevolution.catalog.domain.exception.variant.VariantNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.media.ProductMedia;
import dev.chaunm.commerceevolution.catalog.domain.model.media.MediaView;
import dev.chaunm.commerceevolution.catalog.domain.model.media.valueobject.MediaId;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductStatus;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.Slug;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.ProductVariant;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.VariantView;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.Money;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.VariantId;
import dev.chaunm.commerceevolution.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Getter
    private Instant deletedAt;
    private final List<ProductVariant> variants;
    private final List<ProductMedia> medias;

    public Product(
            ProductId id,
            ProductName name,
            Slug slug,
            CategoryId categoryId,
            BrandId brandId,
            ProductStatus status,
            Instant deletedAt,
            List<ProductVariant> variants,
            List<ProductMedia> medias
    ) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.status = status;
        this.deletedAt = deletedAt;
        this.variants = new ArrayList<>(variants);
        this.medias = new ArrayList<>(medias);
    }

    public void updateDetails(ProductName name, Slug slug) {
        assertMutable();
        this.name = name;
        this.slug = slug;
        registerEvent(new ProductUpdatedEvent(this.id, this.name, this.slug));
    }

    /**
     * Guards every mutator that changes a product's content (as opposed to its own
     * lifecycle status). Centralized here so archived/deleted coverage can't silently drift
     * out of sync across mutators the way category/brand assignment once did.
     */
    private void assertMutable() {
        if (this.status == ProductStatus.ARCHIVED) {
            throw new ProductArchivedException();
        }
        if (isDeleted()) {
            throw new ProductDeletedException();
        }
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

    public VariantView addVariant(SKU sku, String name, Money price) {
        assertMutable();
        boolean duplicateInProduct = variants.stream()
                .anyMatch(variant -> variant.getSku().equals(sku));
        if (duplicateInProduct) {
            throw new DuplicateVariantSkuException(sku);
        }

        ProductVariant variant = new ProductVariant(VariantId.generate(), sku, name, true, price);
        variants.add(variant);
        registerEvent(new VariantAddedEvent(this.id, variant.getId(), variant.getSku()));

        return variant;
    }

    public void removeVariant(VariantId variantId) {
        assertMutable();

        ProductVariant variant = findVariant(variantId);

        variants.remove(variant);
        registerEvent(new VariantRemovedEvent(this.id, variantId));
    }

    public VariantView updateVariant(VariantId variantId, SKU sku, String name) {
        assertMutable();

        ProductVariant variant = findVariant(variantId);

        boolean duplicateInProduct = variants.stream()
                .anyMatch(v -> !v.getId().equals(variantId) && v.getSku().equals(sku));
        if (duplicateInProduct) {
            throw new DuplicateVariantSkuException(sku);
        }

        variant.update(sku, name);
        registerEvent(new VariantUpdatedEvent(this.id, variant.getId(), variant.getSku(), variant.getName()));

        return variant;
    }

    public VariantView changeVariantPrice(VariantId variantId, Money price) {
        assertMutable();

        ProductVariant variant = findVariant(variantId);
        variant.changePrice(price);
        registerEvent(new VariantPriceChangedEvent(this.id, variant.getId(), variant.getPrice()));

        return variant;
    }

    public VariantView enableVariant(VariantId variantId) {
        assertMutable();

        ProductVariant variant = findVariant(variantId);
        if (variant.isActive()) {
            throw new VariantAlreadyActiveException();
        }

        variant.activate();
        registerEvent(new VariantEnabledEvent(this.id, variant.getId()));

        return variant;
    }

    public VariantView disableVariant(VariantId variantId) {
        assertMutable();

        ProductVariant variant = findVariant(variantId);
        if (!variant.isActive()) {
            throw new VariantAlreadyInactiveException();
        }

        variant.deactivate();
        registerEvent(new VariantDisabledEvent(this.id, variant.getId()));

        return variant;
    }

    public VariantView getVariant(VariantId variantId) {
        return findVariant(variantId);
    }

    private ProductVariant findVariant(VariantId variantId) {
        return variants.stream()
                .filter(v -> v.getId().equals(variantId))
                .findFirst()
                .orElseThrow(VariantNotFoundException::new);
    }

    public MediaView addMedia(String url, boolean primary) {
        assertMutable();
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

    public void removeMedia(MediaId mediaId) {
        assertMutable();

        ProductMedia media = findMedia(mediaId);

        medias.remove(media);
        registerEvent(new MediaRemovedEvent(this.id, mediaId));
    }

    public MediaView updateMedia(MediaId mediaId, String url) {
        assertMutable();
        if (url == null || url.isBlank()) {
            throw new InvalidMediaUrlException(url);
        }

        ProductMedia media = findMedia(mediaId);
        media.update(url);
        registerEvent(new MediaUpdatedEvent(this.id, media.getId(), media.getUrl()));

        return media;
    }

    public MediaView markThumbnail(MediaId mediaId) {
        assertMutable();

        ProductMedia media = findMedia(mediaId);
        if (media.isPrimary()) {
            throw new MediaAlreadyPrimaryException();
        }

        medias.forEach(ProductMedia::unmarkPrimary);
        media.markPrimary();
        registerEvent(new ThumbnailChangedEvent(this.id, media.getId()));

        return media;
    }

    public void reorderMedia(List<MediaId> orderedMediaIds) {
        assertMutable();

        Set<MediaId> currentIds = medias.stream().map(ProductMedia::getId).collect(Collectors.toSet());
        boolean sameSet = orderedMediaIds.size() == currentIds.size() && currentIds.containsAll(orderedMediaIds);
        if (!sameSet) {
            throw new InvalidMediaOrderException();
        }

        for (int i = 0; i < orderedMediaIds.size(); i++) {
            findMedia(orderedMediaIds.get(i)).reorder(i);
        }

        registerEvent(new MediaReorderedEvent(this.id, orderedMediaIds));
    }

    private ProductMedia findMedia(MediaId mediaId) {
        return medias.stream()
                .filter(m -> m.getId().equals(mediaId))
                .findFirst()
                .orElseThrow(MediaNotFoundException::new);
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public void delete() {
        if (isDeleted()) {
            throw new ProductAlreadyDeletedException();
        }
        this.deletedAt = Instant.now();
        registerEvent(new ProductDeletedEvent(this.id));
    }

    public void restore() {
        if (!isDeleted()) {
            throw new ProductNotDeletedException();
        }
        this.deletedAt = null;
        registerEvent(new ProductRestoredEvent(this.id));
    }

    public void assignCategory(CategoryId categoryId) {
        assertMutable();
        this.categoryId = categoryId;
        registerEvent(new CategoryAssignedEvent(this.id, categoryId));
    }

    public void removeCategory() {
        assertMutable();
        this.categoryId = null;
        registerEvent(new CategoryUnassignedEvent(this.id));
    }

    public void changeBrand(BrandId brandId) {
        assertMutable();
        this.brandId = brandId;
        registerEvent(new BrandChangedEvent(this.id, brandId));
    }

    public void removeBrand() {
        assertMutable();
        this.brandId = null;
        registerEvent(new BrandUnassignedEvent(this.id));
    }

    public List<? extends VariantView> getVariants() {
        return Collections.unmodifiableList(variants);
    }

    public List<? extends MediaView> getMedias() {
        return Collections.unmodifiableList(medias);
    }
}
