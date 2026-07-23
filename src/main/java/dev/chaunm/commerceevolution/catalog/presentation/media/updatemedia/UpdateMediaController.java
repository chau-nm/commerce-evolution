package dev.chaunm.commerceevolution.catalog.presentation.media.updatemedia;

import dev.chaunm.commerceevolution.catalog.application.usecase.media.updatemedia.UpdateMediaMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.media.updatemedia.UpdateMediaResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.media.updatemedia.UpdateMediaUseCase;
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
@RequestMapping("/api/v1/catalog/products/{productId}/media/{mediaId}")
@RequiredArgsConstructor
public class UpdateMediaController {

    private final UpdateMediaUseCase updateMediaUseCase;
    private final UpdateMediaMapper mapper;

    @PutMapping
    public ResponseEntity<UpdateMediaResponse> update(
            @PathVariable UUID productId,
            @PathVariable UUID mediaId,
            @Valid @RequestBody UpdateMediaRequest request
    ) {
        UpdateMediaResult result = updateMediaUseCase.update(mapper.toCommand(productId, mediaId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
