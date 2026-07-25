package dev.chaunm.commerceevolution.authentication.application.usecase.login;

import dev.chaunm.commerceevolution.authentication.presentation.login.LoginRequest;
import dev.chaunm.commerceevolution.authentication.presentation.login.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    LoginCommand toCommand(LoginRequest request);
    LoginResponse toResponse(LoginResult result);
}
