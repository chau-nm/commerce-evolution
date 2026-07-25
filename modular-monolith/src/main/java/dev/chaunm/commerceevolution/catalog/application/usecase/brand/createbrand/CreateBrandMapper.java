package dev.chaunm.commerceevolution.catalog.application.usecase.brand.createbrand;

import dev.chaunm.commerceevolution.catalog.presentation.brand.createbrand.CreateBrandRequest;
import dev.chaunm.commerceevolution.catalog.presentation.brand.createbrand.CreateBrandResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateBrandMapper {
    CreateBrandCommand toCommand(CreateBrandRequest request);
    CreateBrandResponse toResponse(CreateBrandResult result);
}
