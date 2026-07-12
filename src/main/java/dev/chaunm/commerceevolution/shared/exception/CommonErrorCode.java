package dev.chaunm.commerceevolution.shared.exception;

/** Error codes not tied to a specific bounded context. */
public enum CommonErrorCode implements ErrorCode {
    VALIDATION_FAILED;

    @Override
    public String code() {
        return name();
    }
}
