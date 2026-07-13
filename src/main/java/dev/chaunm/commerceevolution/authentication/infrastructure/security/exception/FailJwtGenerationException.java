package dev.chaunm.commerceevolution.authentication.infrastructure.security.exception;

public class FailJwtGenerationException extends RuntimeException {
    public FailJwtGenerationException(String message) {
        super(message);
    }
}
