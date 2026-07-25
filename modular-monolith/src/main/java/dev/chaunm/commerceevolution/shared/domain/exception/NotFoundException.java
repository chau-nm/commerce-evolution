package dev.chaunm.commerceevolution.shared.domain.exception;

/** The requested aggregate does not exist. */
public abstract class NotFoundException extends DomainException {

    protected NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
