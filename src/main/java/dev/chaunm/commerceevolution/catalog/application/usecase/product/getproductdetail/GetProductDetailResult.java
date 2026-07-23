package dev.chaunm.commerceevolution.catalog.application.usecase.product.getproductdetail;

import java.util.List;
import java.util.UUID;

public record GetProductDetailResult(
        UUID id,
        String name,
        String slug,
        UUID categoryId,
        UUID brandId,
        String status,
        List<VariantDetail> variants,
        List<MediaDetail> medias
) {
    public record VariantDetail(UUID id, String sku, String name, boolean active) {
    }

    public record MediaDetail(UUID id, String url, int sortOrder, boolean primary) {
    }
}
