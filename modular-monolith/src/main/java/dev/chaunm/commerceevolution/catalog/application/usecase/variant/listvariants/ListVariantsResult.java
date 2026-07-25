package dev.chaunm.commerceevolution.catalog.application.usecase.variant.listvariants;

import java.util.List;
import java.util.UUID;

public record ListVariantsResult(List<VariantItem> variants) {
    public record VariantItem(UUID id, String sku, String name, boolean active, long price) {
    }
}
