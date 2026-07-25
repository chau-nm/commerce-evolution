package dev.chaunm.commerceevolution.catalog.application.usecase.category.updatecategory;

import dev.chaunm.commerceevolution.catalog.domain.exception.category.CategoryNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.category.Category;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryName;
import dev.chaunm.commerceevolution.catalog.domain.repository.category.CategoryRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCategoryUseCaseImpl implements UpdateCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public UpdateCategoryResult update(UpdateCategoryCommand command) {
        Category category = categoryRepository.findById(new CategoryId(command.id()))
                .orElseThrow(CategoryNotFoundException::new);

        category.updateName(new CategoryName(command.name()));

        Category savedCategory = categoryRepository.save(category);
        category.domainEvents().forEach(domainEventPublisher::publish);

        return new UpdateCategoryResult(savedCategory.getId().value(), savedCategory.getName().value());
    }
}
