package dev.chaunm.commerceevolution.catalog.presentation.media.setthumbnail;

import dev.chaunm.commerceevolution.catalog.application.usecase.media.setthumbnail.SetThumbnailMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.media.setthumbnail.SetThumbnailResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.media.setthumbnail.SetThumbnailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/media/{mediaId}/thumbnail")
@RequiredArgsConstructor
public class SetThumbnailController {

    private final SetThumbnailUseCase setThumbnailUseCase;
    private final SetThumbnailMapper mapper;

    @PostMapping
    public ResponseEntity<SetThumbnailResponse> setThumbnail(
            @PathVariable UUID productId,
            @PathVariable UUID mediaId
    ) {
        SetThumbnailResult result = setThumbnailUseCase.setThumbnail(mapper.toCommand(productId, mediaId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
