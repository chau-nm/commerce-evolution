package dev.chaunm.commerceevolution.catalog.application.usecase.category.createcategory;

import dev.chaunm.commerceevolution.catalog.domain.exception.category.ParentCategoryNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.factory.category.CategoryFactory;
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
public class CreateCategoryUseCaseImpl implements CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public CreateCategoryResult create(CreateCategoryCommand command) {
        CategoryId parentId = command.parentId() == null ? null : new CategoryId(command.parentId());
        if (parentId != null && categoryRepository.findById(parentId).isEmpty()) {
            throw new ParentCategoryNotFoundException();
        }

        Category category = CategoryFactory.create(new CategoryName(command.name()), parentId);

        Category savedCategory = categoryRepository.save(category);
        category.domainEvents().forEach(domainEventPublisher::publish);

        return new CreateCategoryResult(
                savedCategory.getId().value(),
                savedCategory.getName().value(),
                savedCategory.getParentId() == null ? null : savedCategory.getParentId().value()
        );
    }
}
