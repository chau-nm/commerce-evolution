package dev.chaunm.commerceevolution.catalog.domain.model;

import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.MediaId;
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
}
