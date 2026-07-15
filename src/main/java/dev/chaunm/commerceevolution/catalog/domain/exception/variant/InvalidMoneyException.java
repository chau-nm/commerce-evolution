package dev.chaunm.commerceevolution.catalog.domain.exception.variant;

import dev.chaunm.commerceevolution.catalog.domain.exception.CatalogErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidMoneyException extends DomainException {
    public InvalidMoneyException(long amount) {
        super(CatalogErrorCode.INVALID_PRICE, "Invalid price: " + amount);
    }
}
