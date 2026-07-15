package dev.chaunm.commerceevolution.catalog.presentation.product.getproductdetail;

import java.util.List;
import java.util.UUID;

public record GetProductDetailResponse(
        UUID id,
        String name,
        String slug,
        UUID categoryId,
        UUID brandId,
        String status,
        List<VariantResponse> variants,
        List<MediaResponse> medias
) {
    public record VariantResponse(UUID id, String sku, String name, boolean active) {
    }

    public record MediaResponse(UUID id, String url, int sortOrder, boolean primary) {
    }
}
