package dev.chaunm.commerceevolution.authentication.application.usecase.logout;

import dev.chaunm.commerceevolution.authentication.presentation.logout.LogoutRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LogoutMapper {
    LogoutCommand toCommand(LogoutRequest request);
}
