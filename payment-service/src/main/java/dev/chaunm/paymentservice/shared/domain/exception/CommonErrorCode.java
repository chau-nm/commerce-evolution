package dev.chaunm.paymentservice.shared.domain.exception;

/** Error codes not tied to the payment domain specifically. */
public enum CommonErrorCode implements ErrorCode {
    VALIDATION_FAILED,
    UNAUTHENTICATED,
    CONCURRENT_MODIFICATION;

    @Override
    public String code() {
        return name();
    }
}
