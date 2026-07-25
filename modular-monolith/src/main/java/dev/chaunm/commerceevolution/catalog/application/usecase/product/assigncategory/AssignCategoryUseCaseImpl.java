package dev.chaunm.commerceevolution.catalog.application.usecase.product.assigncategory;

import dev.chaunm.commerceevolution.catalog.domain.exception.category.CategoryNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.repository.category.CategoryRepository;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssignCategoryUseCaseImpl implements AssignCategoryUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public AssignCategoryResult assignCategory(AssignCategoryCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        CategoryId categoryId = new CategoryId(command.categoryId());
        if (categoryRepository.findById(categoryId).isEmpty()) {
            throw new CategoryNotFoundException();
        }

        product.assignCategory(categoryId);

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new AssignCategoryResult(savedProduct.getId().value(), savedProduct.getCategoryId().value());
    }
}
