package dev.chaunm.commerceevolution.shared.domain.exception;

/** A domain rule was violated because the requested state already exists (e.g. duplicate email). */
public abstract class ConflictException extends DomainException {

    protected ConflictException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
