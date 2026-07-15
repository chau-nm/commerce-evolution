package dev.chaunm.commerceevolution.catalog.presentation.listvariants;

import java.util.List;
import java.util.UUID;

public record ListVariantsResponse(List<VariantResponse> variants) {
    public record VariantResponse(UUID id, String sku, String name, boolean active) {
    }
}
