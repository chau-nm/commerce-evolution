package dev.chaunm.commerceevolution.catalog.application.usecase.product.changebrand;

import dev.chaunm.commerceevolution.catalog.presentation.product.changebrand.ChangeBrandRequest;
import dev.chaunm.commerceevolution.catalog.presentation.product.changebrand.ChangeBrandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ChangeBrandMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "brandId", source = "request.brandId")
    ChangeBrandCommand toCommand(UUID productId, ChangeBrandRequest request);

    ChangeBrandResponse toResponse(ChangeBrandResult result);
}
