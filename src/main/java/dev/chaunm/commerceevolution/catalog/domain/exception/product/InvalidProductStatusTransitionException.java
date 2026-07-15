package dev.chaunm.commerceevolution.catalog.domain.exception.product;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductStatus;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidProductStatusTransitionException extends DomainException {
    public InvalidProductStatusTransitionException(ProductStatus from, ProductStatus to) {
        super(
                CatalogErrorCode.INVALID_STATUS_TRANSITION,
                "Cannot transition product from " + from + " to " + to
        );
    }
}
