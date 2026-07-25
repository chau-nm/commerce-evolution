package dev.chaunm.commerceevolution.catalog.application.usecase.brand.restorebrand;

import dev.chaunm.commerceevolution.catalog.presentation.brand.restorebrand.RestoreBrandResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestoreBrandMapper {
    RestoreBrandCommand toCommand(UUID id);
    RestoreBrandResponse toResponse(RestoreBrandResult result);
}
