package dev.chaunm.commerceevolution.catalog.application.usecase.product.createproduct;

import dev.chaunm.commerceevolution.catalog.domain.exception.brand.BrandNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.exception.category.CategoryNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.exception.product.DuplicateSlugException;
import dev.chaunm.commerceevolution.catalog.domain.factory.product.ProductFactory;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.Slug;
import dev.chaunm.commerceevolution.catalog.domain.repository.brand.BrandRepository;
import dev.chaunm.commerceevolution.catalog.domain.repository.category.CategoryRepository;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public CreateProductResult create(CreateProductCommand command) {
        Slug slug = new Slug(command.slug());
        if (productRepository.existsBySlug(slug)) {
            throw new DuplicateSlugException(slug);
        }

        CategoryId categoryId = command.categoryId() == null ? null : new CategoryId(command.categoryId());
        if (categoryId != null && categoryRepository.findById(categoryId).isEmpty()) {
            throw new CategoryNotFoundException();
        }

        BrandId brandId = command.brandId() == null ? null : new BrandId(command.brandId());
        if (brandId != null && brandRepository.findById(brandId).isEmpty()) {
            throw new BrandNotFoundException();
        }

        Product product = ProductFactory.create(
                new ProductName(command.name()),
                slug,
                categoryId,
                brandId
        );

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new CreateProductResult(savedProduct.getId().value());
    }
}
