package dev.chaunm.commerceevolution.catalog.application.usecase.brand.updatebrand;

import dev.chaunm.commerceevolution.catalog.presentation.brand.updatebrand.UpdateBrandRequest;
import dev.chaunm.commerceevolution.catalog.presentation.brand.updatebrand.UpdateBrandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdateBrandMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "request.name")
    UpdateBrandCommand toCommand(UUID id, UpdateBrandRequest request);

    UpdateBrandResponse toResponse(UpdateBrandResult result);
}
