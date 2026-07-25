package dev.chaunm.commerceevolution.catalog.presentation.product.archiveproduct;

import dev.chaunm.commerceevolution.catalog.application.usecase.product.archiveproduct.ArchiveProductMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.archiveproduct.ArchiveProductResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.archiveproduct.ArchiveProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{id}/archive")
@RequiredArgsConstructor
public class ArchiveProductController {

    private final ArchiveProductUseCase archiveProductUseCase;
    private final ArchiveProductMapper mapper;

    @PostMapping
    public ResponseEntity<ArchiveProductResponse> archive(@PathVariable UUID id) {
        ArchiveProductResult result = archiveProductUseCase.archive(mapper.toCommand(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
