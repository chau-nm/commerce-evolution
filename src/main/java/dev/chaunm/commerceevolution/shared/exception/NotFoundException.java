package dev.chaunm.commerceevolution.shared.exception;

/** The requested aggregate does not exist. */
public abstract class NotFoundException extends DomainException {

    protected NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
