package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.repository.category;

import dev.chaunm.commerceevolution.catalog.domain.model.category.Category;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.repository.category.CategoryRepository;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.category.CategoryEntity;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper.category.CategoryMapper;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Optional<Category> findById(CategoryId id) {
        return jpaCategoryRepository.findById(id.value())
                .map(categoryMapper::toDomain);
    }

    @Override
    public PaginationResult<Category> findAll(CategoryId parentId, PaginationQuery query) {
        Pageable pageable = query.toPageable();

        Page<CategoryEntity> page = parentId == null
                ? jpaCategoryRepository.findByDeletedAtIsNull(pageable)
                : jpaCategoryRepository.findByParentIdAndDeletedAtIsNull(parentId.value(), pageable);

        return PaginationResult.from(page)
                .map(categoryMapper::toDomain);
    }

    @Override
    public Category save(Category category) {
        return categoryMapper.toDomain(
                jpaCategoryRepository.save(categoryMapper.toEntity(category))
        );
    }
}
