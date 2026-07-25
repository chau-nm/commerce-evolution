package dev.chaunm.commerceevolution.shared.domain.exception;

/** No authenticated principal is present in the security context for a request that requires one. */
public class UnauthenticatedException extends UnauthorizedException {

    public UnauthenticatedException() {
        super(CommonErrorCode.UNAUTHENTICATED, "Authentication is required to access this resource");
    }
}
