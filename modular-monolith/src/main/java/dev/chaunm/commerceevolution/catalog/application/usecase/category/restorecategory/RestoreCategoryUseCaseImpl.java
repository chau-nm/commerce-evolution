package dev.chaunm.commerceevolution.catalog.application.usecase.category.restorecategory;

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
public class RestoreCategoryUseCaseImpl implements RestoreCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public RestoreCategoryResult restore(RestoreCategoryCommand command) {
        Category category = categoryRepository.findById(new CategoryId(command.id()))
                .orElseThrow(CategoryNotFoundException::new);

        category.restore();

        Category savedCategory = categoryRepository.save(category);
        category.domainEvents().forEach(domainEventPublisher::publish);

        return new RestoreCategoryResult(savedCategory.getId().value());
    }
}
