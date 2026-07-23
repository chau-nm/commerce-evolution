package dev.chaunm.commerceevolution.catalog.application.usecase.category.listcategories;

import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.repository.category.CategoryRepository;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListCategoriesUseCaseImpl implements ListCategoriesUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public PaginationResult<CategorySummaryItem> list(ListCategoriesCommand command) {
        CategoryId parentId = command.parentId() == null ? null : new CategoryId(command.parentId());
        PaginationQuery query = PaginationQuery.from(command.pagination());

        return categoryRepository.findAll(parentId, query)
                .map(category -> new CategorySummaryItem(
                        category.getId().value(),
                        category.getName().value(),
                        category.getParentId() == null ? null : category.getParentId().value()
                ));
    }
}
