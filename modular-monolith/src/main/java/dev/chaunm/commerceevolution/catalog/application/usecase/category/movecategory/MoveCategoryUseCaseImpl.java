package dev.chaunm.commerceevolution.catalog.application.usecase.category.movecategory;

import dev.chaunm.commerceevolution.catalog.domain.exception.category.CategoryNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.exception.category.CircularCategoryReferenceException;
import dev.chaunm.commerceevolution.catalog.domain.exception.category.ParentCategoryNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.category.Category;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.repository.category.CategoryRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MoveCategoryUseCaseImpl implements MoveCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public MoveCategoryResult move(MoveCategoryCommand command) {
        Category category = categoryRepository.findById(new CategoryId(command.id()))
                .orElseThrow(CategoryNotFoundException::new);

        CategoryId newParentId = command.parentId() == null ? null : new CategoryId(command.parentId());
        if (newParentId != null) {
            assertNoCycle(category.getId(), newParentId);
        }

        category.moveTo(newParentId);

        Category savedCategory = categoryRepository.save(category);
        category.domainEvents().forEach(domainEventPublisher::publish);

        return new MoveCategoryResult(
                savedCategory.getId().value(),
                savedCategory.getParentId() == null ? null : savedCategory.getParentId().value()
        );
    }

    private void assertNoCycle(CategoryId categoryId, CategoryId newParentId) {
        CategoryId current = newParentId;
        while (current != null) {
            if (current.equals(categoryId)) {
                throw new CircularCategoryReferenceException();
            }
            current = categoryRepository.findById(current)
                    .orElseThrow(ParentCategoryNotFoundException::new)
                    .getParentId();
        }
    }
}
