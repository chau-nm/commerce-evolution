package dev.chaunm.commerceevolution.catalog.application.usecase.media.uploadmedia;

import dev.chaunm.commerceevolution.catalog.presentation.media.uploadmedia.UploadMediaResponse;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UploadMediaMapper {

    default UploadMediaCommand toCommand(UUID productId, MultipartFile file, boolean primary) {
        try {
            return new UploadMediaCommand(
                    productId,
                    file.getOriginalFilename(),
                    file.getBytes(),
                    file.getContentType(),
                    primary
            );
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    UploadMediaResponse toResponse(UploadMediaResult result);
}
