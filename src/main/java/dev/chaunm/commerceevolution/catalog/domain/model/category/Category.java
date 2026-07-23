package dev.chaunm.commerceevolution.catalog.domain.model.category;

import dev.chaunm.commerceevolution.catalog.domain.event.category.CategoryDeletedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.category.CategoryMovedEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.category.CategoryRestoredEvent;
import dev.chaunm.commerceevolution.catalog.domain.event.category.CategoryUpdatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.exception.category.CategoryAlreadyDeletedException;
import dev.chaunm.commerceevolution.catalog.domain.exception.category.CategoryCannotBeOwnParentException;
import dev.chaunm.commerceevolution.catalog.domain.exception.category.CategoryNotDeletedException;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryName;
import dev.chaunm.commerceevolution.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.time.Instant;

public class Category extends AggregateRoot {

    @Getter
    private final CategoryId id;
    @Getter
    private CategoryName name;
    @Getter
    private CategoryId parentId;
    @Getter
    private Instant deletedAt;

    public Category(
            CategoryId id,
            CategoryName name,
            CategoryId parentId,
            Instant deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.deletedAt = deletedAt;
    }

    public void updateName(CategoryName name) {
        this.name = name;
        registerEvent(new CategoryUpdatedEvent(this.id, this.name));
    }

    public void moveTo(CategoryId newParentId) {
        if (newParentId != null && newParentId.equals(this.id)) {
            throw new CategoryCannotBeOwnParentException();
        }
        this.parentId = newParentId;
        registerEvent(new CategoryMovedEvent(this.id, newParentId));
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public void delete() {
        if (isDeleted()) {
            throw new CategoryAlreadyDeletedException();
        }
        this.deletedAt = Instant.now();
        registerEvent(new CategoryDeletedEvent(this.id));
    }

    public void restore() {
        if (!isDeleted()) {
            throw new CategoryNotDeletedException();
        }
        this.deletedAt = null;
        registerEvent(new CategoryRestoredEvent(this.id));
    }
}
