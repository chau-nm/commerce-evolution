package dev.chaunm.commerceevolution.catalog.application.usecase.category.deletecategory;

import dev.chaunm.commerceevolution.catalog.domain.exception.category.CategoryNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.category.Category;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.repository.category.CategoryRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCategoryUseCaseImpl implements DeleteCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public DeleteCategoryResult delete(DeleteCategoryCommand command) {
        Category category = categoryRepository.findById(new CategoryId(command.id()))
                .orElseThrow(CategoryNotFoundException::new);

        category.delete();

        Category savedCategory = categoryRepository.save(category);
        category.domainEvents().forEach(domainEventPublisher::publish);

        return new DeleteCategoryResult(savedCategory.getId().value(), savedCategory.getDeletedAt());
    }
}
