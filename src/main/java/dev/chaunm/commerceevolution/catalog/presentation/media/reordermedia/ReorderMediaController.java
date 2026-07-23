package dev.chaunm.commerceevolution.catalog.presentation.media.reordermedia;

import dev.chaunm.commerceevolution.catalog.application.usecase.media.reordermedia.ReorderMediaMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.media.reordermedia.ReorderMediaResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.media.reordermedia.ReorderMediaUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/media/reorder")
@RequiredArgsConstructor
public class ReorderMediaController {

    private final ReorderMediaUseCase reorderMediaUseCase;
    private final ReorderMediaMapper mapper;

    @PutMapping
    public ResponseEntity<ReorderMediaResponse> reorder(
            @PathVariable UUID productId,
            @Valid @RequestBody ReorderMediaRequest request
    ) {
        ReorderMediaResult result = reorderMediaUseCase.reorder(mapper.toCommand(productId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
