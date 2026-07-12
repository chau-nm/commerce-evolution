package dev.chaunm.commerceevolution.shared.exception;

/** A domain rule was violated because the requested state already exists (e.g. duplicate email). */
public abstract class ConflictException extends DomainException {

    protected ConflictException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
