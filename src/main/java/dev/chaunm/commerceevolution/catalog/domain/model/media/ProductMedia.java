package dev.chaunm.commerceevolution.catalog.domain.model.media;

import dev.chaunm.commerceevolution.catalog.domain.model.media.valueobject.MediaId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductMedia {
    private final MediaId id;
    private String url;
    private int sortOrder;
    private boolean primary;

    public void unmarkPrimary() {
        this.primary = false;
    }

    public void markPrimary() {
        this.primary = true;
    }

    public void update(String url) {
        this.url = url;
    }

    public void reorder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
