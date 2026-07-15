package dev.chaunm.commerceevolution.catalog.domain.model.brand;

import dev.chaunm.commerceevolution.catalog.domain.event.brand.BrandDeletedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.brand.BrandRestoredEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.brand.BrandUpdatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.exception.brand.BrandAlreadyDeletedException;
import dev.chaunm.commerceevolution.catalog.domain.exception.brand.BrandNotDeletedException;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandName;
import dev.chaunm.commerceevolution.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.time.Instant;

public class Brand extends AggregateRoot {

    @Getter
    private final BrandId id;
    @Getter
    private BrandName name;
    @Getter
    private Instant deletedAt;

    public Brand(
            BrandId id,
            BrandName name,
            Instant deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.deletedAt = deletedAt;
    }

    public void updateName(BrandName name) {
        this.name = name;
        registerEvent(new BrandUpdatedEvent(this.id, this.name));
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public void delete() {
        if (isDeleted()) {
            throw new BrandAlreadyDeletedException();
        }
        this.deletedAt = Instant.now();
        registerEvent(new BrandDeletedEvent(this.id));
    }

    public void restore() {
        if (!isDeleted()) {
            throw new BrandNotDeletedException();
        }
        this.deletedAt = null;
        registerEvent(new BrandRestoredEvent(this.id));
    }
}
