package dev.chaunm.commerceevolution.authentication.application.usecase.register;

import dev.chaunm.commerceevolution.authentication.presentation.register.RegisterRequest;
import dev.chaunm.commerceevolution.authentication.presentation.register.RegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    RegisterCommand toCommand(RegisterRequest request);
    RegisterResponse toResponse(RegisterResult result);
}
