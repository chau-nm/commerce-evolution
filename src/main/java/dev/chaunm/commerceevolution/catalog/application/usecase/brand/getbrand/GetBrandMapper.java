package dev.chaunm.commerceevolution.catalog.application.usecase.brand.getbrand;

import dev.chaunm.commerceevolution.catalog.presentation.brand.getbrand.GetBrandResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GetBrandMapper {
    GetBrandCommand toCommand(UUID id);
    GetBrandResponse toResponse(GetBrandResult result);
}
