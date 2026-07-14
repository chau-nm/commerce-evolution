package dev.chaunm.commerceevolution.catalog.application.usecase.archiveproduct;

import dev.chaunm.commerceevolution.catalog.presentation.archiveproduct.ArchiveProductResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ArchiveProductMapper {
    ArchiveProductCommand toCommand(UUID id);
    ArchiveProductResponse toResponse(ArchiveProductResult result);
}
