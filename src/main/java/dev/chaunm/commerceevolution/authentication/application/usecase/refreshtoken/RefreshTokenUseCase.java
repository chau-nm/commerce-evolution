package dev.chaunm.commerceevolution.authentication.application.usecase.refreshtoken;

public interface RefreshTokenUseCase {
    RefreshTokenResult refresh(RefreshTokenCommand command);
}
