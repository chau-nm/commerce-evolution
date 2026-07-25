package dev.chaunm.commerceevolution.catalog.domain.factory.category;

import dev.chaunm.commerceevolution.catalog.domain.event.category.CategoryCreatedEvent;
import dev.chaunm.commerceevolution.catalog.domain.model.category.Category;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryName;

public class CategoryFactory {

    public static Category create(CategoryName name, CategoryId parentId) {
        Category category = new Category(
                CategoryId.generate(),
                name,
                parentId,
                null
        );

        category.registerEvent(new CategoryCreatedEvent(category.getId(), category.getName()));

        return category;
    }
}
