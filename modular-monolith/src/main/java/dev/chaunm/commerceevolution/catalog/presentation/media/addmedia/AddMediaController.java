package dev.chaunm.commerceevolution.catalog.presentation.media.addmedia;

import dev.chaunm.commerceevolution.catalog.application.usecase.media.addmedia.AddMediaMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.media.addmedia.AddMediaResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.media.addmedia.AddMediaUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/media")
@RequiredArgsConstructor
public class AddMediaController {

    private final AddMediaUseCase addMediaUseCase;
    private final AddMediaMapper mapper;

    @PostMapping
    public ResponseEntity<AddMediaResponse> addMedia(
            @PathVariable UUID productId,
            @Valid @RequestBody AddMediaRequest request
    ) {
        AddMediaResult result = addMediaUseCase.addMedia(mapper.toCommand(productId, request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
