package dev.chaunm.commerceevolution.shared.domain.exception;

/** Error codes not tied to a specific bounded context. */
public enum CommonErrorCode implements ErrorCode {
    VALIDATION_FAILED,
    UNAUTHENTICATED,
    CONCURRENT_MODIFICATION;

    @Override
    public String code() {
        return name();
    }
}
