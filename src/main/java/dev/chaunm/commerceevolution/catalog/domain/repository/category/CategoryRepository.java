package dev.chaunm.commerceevolution.catalog.domain.repository.category;

import dev.chaunm.commerceevolution.catalog.domain.model.category.Category;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findById(CategoryId id);
    PaginationResult<Category> findAll(CategoryId parentId, PaginationQuery query);
    Category save(Category category);
}
