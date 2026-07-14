package dev.chaunm.commerceevolution.catalog.application.usecase.createproduct;

import dev.chaunm.commerceevolution.catalog.domain.exception.DuplicateSlugException;
import dev.chaunm.commerceevolution.catalog.domain.factory.ProductFactory;
import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.Slug;
import dev.chaunm.commerceevolution.catalog.domain.repository.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private final ProductRepository productRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public CreateProductResult create(CreateProductCommand command) {
        Slug slug = new Slug(command.slug());
        if (productRepository.existsBySlug(slug)) {
            throw new DuplicateSlugException(slug);
        }

        Product product = ProductFactory.create(
                new ProductName(command.name()),
                slug,
                new CategoryId(command.categoryId()),
                new BrandId(command.brandId())
        );

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new CreateProductResult(savedProduct.getId().value());
    }
}
