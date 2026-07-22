package dev.chaunm.commerceevolution.payment.domain.exception;

import dev.chaunm.commerceevolution.payment.domain.model.valueobject.PaymentStatus;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;

public class InvalidPaymentStatusTransitionException extends DomainException {
    public InvalidPaymentStatusTransitionException(PaymentStatus from, PaymentStatus to) {
        super(
                PaymentErrorCode.INVALID_STATUS_TRANSITION,
                "Cannot transition payment from " + from + " to " + to
        );
    }
}
