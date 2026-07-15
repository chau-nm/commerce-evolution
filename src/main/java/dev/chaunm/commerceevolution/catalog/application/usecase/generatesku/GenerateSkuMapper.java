package dev.chaunm.commerceevolution.catalog.application.usecase.generatesku;

import dev.chaunm.commerceevolution.catalog.presentation.generatesku.GenerateSkuRequest;
import dev.chaunm.commerceevolution.catalog.presentation.generatesku.GenerateSkuResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GenerateSkuMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "variantName", source = "request.variantName")
    GenerateSkuCommand toCommand(UUID productId, GenerateSkuRequest request);

    GenerateSkuResponse toResponse(GenerateSkuResult result);
}
