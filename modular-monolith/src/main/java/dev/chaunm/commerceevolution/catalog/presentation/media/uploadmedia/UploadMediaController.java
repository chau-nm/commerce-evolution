package dev.chaunm.commerceevolution.catalog.presentation.media.uploadmedia;

import dev.chaunm.commerceevolution.catalog.application.usecase.media.uploadmedia.UploadMediaMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.media.uploadmedia.UploadMediaResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.media.uploadmedia.UploadMediaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/media/upload")
@RequiredArgsConstructor
public class UploadMediaController {

    private final UploadMediaUseCase uploadMediaUseCase;
    private final UploadMediaMapper mapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadMediaResponse> upload(
            @PathVariable UUID productId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "false") boolean primary
    ) {
        UploadMediaResult result = uploadMediaUseCase.upload(mapper.toCommand(productId, file, primary));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
