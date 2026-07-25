package dev.chaunm.commerceevolution.authentication.application.usecase.refreshtoken;

import dev.chaunm.commerceevolution.authentication.presentation.refreshtoken.RefreshTokenRequest;
import dev.chaunm.commerceevolution.authentication.presentation.refreshtoken.RefreshTokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {
    RefreshTokenCommand toCommand(RefreshTokenRequest request);
    RefreshTokenResponse toResponse(RefreshTokenResult result);
}
