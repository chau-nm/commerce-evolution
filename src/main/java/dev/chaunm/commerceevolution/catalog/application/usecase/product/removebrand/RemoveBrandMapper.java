package dev.chaunm.commerceevolution.catalog.application.usecase.product.removebrand;

import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RemoveBrandMapper {
    RemoveBrandCommand toCommand(UUID productId);
}
