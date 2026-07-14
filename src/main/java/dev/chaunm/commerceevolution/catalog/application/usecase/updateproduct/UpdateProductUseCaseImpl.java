package dev.chaunm.commerceevolution.catalog.application.usecase.updateproduct;

import dev.chaunm.commerceevolution.catalog.domain.exception.DuplicateSlugException;
import dev.chaunm.commerceevolution.catalog.domain.exception.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.Slug;
import dev.chaunm.commerceevolution.catalog.domain.repository.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private final ProductRepository productRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public UpdateProductResult update(UpdateProductCommand command) {
        ProductId productId = new ProductId(command.id());
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        Slug slug = new Slug(command.slug());
        if (productRepository.existsBySlugAndIdNot(slug, productId)) {
            throw new DuplicateSlugException(slug);
        }

        product.updateDetails(new ProductName(command.name()), slug);

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new UpdateProductResult(savedProduct.getId().value());
    }
}
