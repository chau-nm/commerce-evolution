package dev.chaunm.commerceevolution.shared.domain.exception;

import lombok.Getter;

/**
 * Base type for exceptions that represent a violation of a business rule.
 * Kept free of any web/framework concerns; {@code GlobalExceptionHandler}
 * is responsible for translating these into HTTP responses.
 */
@Getter
public abstract class DomainException extends RuntimeException {

    private final ErrorCode errorCode;

    protected DomainException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
