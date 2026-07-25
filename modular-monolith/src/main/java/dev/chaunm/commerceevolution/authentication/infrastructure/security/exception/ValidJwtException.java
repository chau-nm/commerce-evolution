package dev.chaunm.commerceevolution.authentication.infrastructure.security.exception;

public class ValidJwtException extends RuntimeException {
    public ValidJwtException(String message) {
        super(message);
    }
}
