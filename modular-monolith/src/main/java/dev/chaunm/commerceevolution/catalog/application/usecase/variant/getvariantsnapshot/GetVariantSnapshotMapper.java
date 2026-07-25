package dev.chaunm.commerceevolution.catalog.application.usecase.variant.getvariantsnapshot;

import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductStatus;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.VariantView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetVariantSnapshotMapper {

    default VariantSnapshotResult toResult(Product product, VariantView variant) {
        boolean purchasable = product.getStatus() == ProductStatus.PUBLISHED
                && !product.isDeleted()
                && variant.isActive();

        return new VariantSnapshotResult(
                variant.getId().value(),
                product.getId().value(),
                product.getName().value(),
                variant.getName(),
                variant.getPrice().amount(),
                purchasable
        );
    }
}
