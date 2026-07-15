package dev.chaunm.commerceevolution.catalog.domain.service.media;

public interface MediaStorage {
    String store(String filename, byte[] content, String contentType);
}
