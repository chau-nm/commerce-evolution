package dev.chaunm.paymentservice.payment.domain.exception;

import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentStatus;
import dev.chaunm.paymentservice.shared.domain.exception.DomainException;

public class InvalidPaymentStatusTransitionException extends DomainException {
    public InvalidPaymentStatusTransitionException(PaymentStatus from, PaymentStatus to) {
        super(
                PaymentErrorCode.INVALID_STATUS_TRANSITION,
                "Cannot transition payment from " + from + " to " + to
        );
    }
}
