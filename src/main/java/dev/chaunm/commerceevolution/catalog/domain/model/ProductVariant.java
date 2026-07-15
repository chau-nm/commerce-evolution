package dev.chaunm.commerceevolution.catalog.domain.model;

import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.VariantId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductVariant {
    private final VariantId id;
    private SKU sku;
    private String name;
    private boolean active;

    public void update(SKU sku, String name) {
        this.sku = sku;
        this.name = name;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
