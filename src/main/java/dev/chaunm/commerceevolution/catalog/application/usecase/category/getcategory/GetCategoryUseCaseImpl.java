package dev.chaunm.commerceevolution.catalog.application.usecase.category.getcategory;

import dev.chaunm.commerceevolution.catalog.domain.exception.category.CategoryNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.category.Category;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCategoryUseCaseImpl implements GetCategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public GetCategoryResult getCategory(GetCategoryCommand command) {
        Category category = categoryRepository.findById(new CategoryId(command.id()))
                .orElseThrow(CategoryNotFoundException::new);

        return new GetCategoryResult(
                category.getId().value(),
                category.getName().value(),
                category.getParentId() == null ? null : category.getParentId().value(),
                category.getDeletedAt()
        );
    }
}
