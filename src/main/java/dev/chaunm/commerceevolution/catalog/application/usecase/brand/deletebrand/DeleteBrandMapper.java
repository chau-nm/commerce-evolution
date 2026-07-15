package dev.chaunm.commerceevolution.catalog.application.usecase.brand.deletebrand;

import dev.chaunm.commerceevolution.catalog.presentation.brand.deletebrand.DeleteBrandResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DeleteBrandMapper {
    DeleteBrandCommand toCommand(UUID id);
    DeleteBrandResponse toResponse(DeleteBrandResult result);
}
