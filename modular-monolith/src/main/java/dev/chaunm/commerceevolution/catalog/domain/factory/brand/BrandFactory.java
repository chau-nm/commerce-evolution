package dev.chaunm.commerceevolution.catalog.domain.factory.brand;

import dev.chaunm.commerceevolution.catalog.domain.event.brand.BrandCreatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.Brand;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandName;

public class BrandFactory {

    public static Brand create(BrandName name) {
        Brand brand = new Brand(
                BrandId.generate(),
                name,
                null
        );

        brand.registerEvent(new BrandCreatedEvent(brand.getId(), brand.getName()));

        return brand;
    }
}
