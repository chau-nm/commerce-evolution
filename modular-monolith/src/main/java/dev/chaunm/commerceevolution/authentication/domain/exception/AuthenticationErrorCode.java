package dev.chaunm.commerceevolution.authentication.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.ErrorCode;

public enum AuthenticationErrorCode implements ErrorCode {
    INVALID_EMAIL("INVALID_EMAIL"),
    EXISTED_EMAIL("EXISTED_EMAIL"),
    INVALID_PASSWORD("INVALID_PASSWORD"),
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND"),
    ACCOUNT_ALREADY_EXISTS("ACCOUNT_ALREADY_EXISTS"),
    INVALID_REFRESH_TOKEN("INVALID_REFRESH_TOKEN");

    private final String code;

    AuthenticationErrorCode(String code) {
        this.code = code;
    }

    @Override
    public String code() {
        return code;
    }
}
