package dev.chaunm.commerceevolution.catalog.domain.model.variant;

import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.Money;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.VariantId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductVariant {
    private final VariantId id;
    private SKU sku;
    private String name;
    private boolean active;
    private Money price;

    public void update(SKU sku, String name) {
        this.sku = sku;
        this.name = name;
    }

    public void changePrice(Money price) {
        this.price = price;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
