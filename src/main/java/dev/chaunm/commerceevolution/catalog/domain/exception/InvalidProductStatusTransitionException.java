package dev.chaunm.commerceevolution.catalog.domain.exception;

import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductStatus;
import dev.chaunm.commerceevolution.shared.exception.DomainException;

public class InvalidProductStatusTransitionException extends DomainException {
    public InvalidProductStatusTransitionException(ProductStatus from, ProductStatus to) {
        super(
                CatalogErrorCode.INVALID_STATUS_TRANSITION,
                "Cannot transition product from " + from + " to " + to
        );
    }
}
