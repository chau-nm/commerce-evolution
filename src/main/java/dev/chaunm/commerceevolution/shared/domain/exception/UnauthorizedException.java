package dev.chaunm.commerceevolution.shared.domain.exception;

/** Credentials or a token failed to authenticate the caller. */
public abstract class UnauthorizedException extends DomainException {

    protected UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
