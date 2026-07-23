package dev.chaunm.commerceevolution.catalog.infrastructure.media;

import dev.chaunm.commerceevolution.catalog.domain.service.media.MediaStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class LocalMediaStorage implements MediaStorage {

    @Value("${app.media.upload-dir:uploads}")
    private String uploadDir;

    @Value("${app.media.public-path:/media}")
    private String publicPath;

    @Override
    public String store(String filename, byte[] content, String contentType) {
        try {
            Path dir = Path.of(uploadDir);
            Files.createDirectories(dir);

            String storedName = UUID.randomUUID() + "-" + sanitize(filename);
            Files.write(dir.resolve(storedName), content);

            return publicPath + "/" + storedName;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String sanitize(String filename) {
        return filename == null || filename.isBlank()
                ? "file"
                : filename.replaceAll("[^A-Za-z0-9._-]", "_");
    }
}
