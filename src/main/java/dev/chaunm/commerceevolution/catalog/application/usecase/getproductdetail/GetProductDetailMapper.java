package dev.chaunm.commerceevolution.catalog.application.usecase.getproductdetail;

import dev.chaunm.commerceevolution.catalog.presentation.getproductdetail.GetProductDetailResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GetProductDetailMapper {
    GetProductDetailCommand toCommand(UUID productId);
    GetProductDetailResponse toResponse(GetProductDetailResult result);
}
