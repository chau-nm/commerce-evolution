package dev.chaunm.commerceevolution.catalog.application.usecase.archiveproduct;

import dev.chaunm.commerceevolution.catalog.domain.exception.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.repository.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArchiveProductUseCaseImpl implements ArchiveProductUseCase {

    private final ProductRepository productRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public ArchiveProductResult archive(ArchiveProductCommand command) {
        Product product = productRepository.findById(new ProductId(command.id()))
                .orElseThrow(ProductNotFoundException::new);

        product.archive();

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new ArchiveProductResult(savedProduct.getId().value(), savedProduct.getStatus().name());
    }
}
