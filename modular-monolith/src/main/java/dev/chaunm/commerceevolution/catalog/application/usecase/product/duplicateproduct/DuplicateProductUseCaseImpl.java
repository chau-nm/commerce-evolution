package dev.chaunm.commerceevolution.catalog.application.usecase.product.duplicateproduct;

import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.factory.product.ProductFactory;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.Slug;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DuplicateProductUseCaseImpl implements DuplicateProductUseCase {

    private static final String COPY_SUFFIX = "-copy";

    private final ProductRepository productRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public DuplicateProductResult duplicate(DuplicateProductCommand command) {
        Product source = productRepository.findById(new ProductId(command.id()))
                .orElseThrow(ProductNotFoundException::new);
        if (source.isDeleted()) {
            throw new ProductNotFoundException();
        }

        ProductName name = new ProductName(source.getName().value() + " (Copy)");
        Slug slug = generateUniqueSlug(source.getSlug());

        Product duplicate = ProductFactory.duplicate(source, name, slug);

        Product savedProduct = productRepository.save(duplicate);
        duplicate.domainEvents().forEach(domainEventPublisher::publish);

        return new DuplicateProductResult(
                savedProduct.getId().value(),
                source.getId().value(),
                savedProduct.getName().value(),
                savedProduct.getSlug().value(),
                savedProduct.getStatus().name()
        );
    }

    private Slug generateUniqueSlug(Slug sourceSlug) {
        Slug candidate = new Slug(sourceSlug.value() + COPY_SUFFIX);
        if (!productRepository.existsBySlug(candidate)) {
            return candidate;
        }
        return new Slug(sourceSlug.value() + COPY_SUFFIX + "-" + UUID.randomUUID().toString().substring(0, 8));
    }
}
