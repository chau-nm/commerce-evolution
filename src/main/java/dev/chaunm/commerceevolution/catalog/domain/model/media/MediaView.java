package dev.chaunm.commerceevolution.catalog.domain.model.media;

import dev.chaunm.commerceevolution.catalog.domain.model.media.valueobject.MediaId;

/**
 * Read-only projection of a {@link ProductMedia}. {@code Product} exposes media through this
 * type so callers outside the aggregate (use cases, mappers) can read but never mutate a media
 * item directly — mutation is only possible through {@code Product}'s own methods, which
 * enforce its invariants (archived/deleted guards, domain events).
 */
public interface MediaView {
    MediaId getId();

    String getUrl();

    int getSortOrder();

    boolean isPrimary();
}
