package dev.chaunm.commerceevolution.catalog.application.usecase.publishproduct;

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
public class PublishProductUseCaseImpl implements PublishProductUseCase {

    private final ProductRepository productRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public PublishProductResult publish(PublishProductCommand command) {
        Product product = productRepository.findById(new ProductId(command.id()))
                .orElseThrow(ProductNotFoundException::new);

        product.publish();

        Product savedProduct = productRepository.save(product);
        savedProduct.domainEvents().forEach(domainEventPublisher::publish);

        return new PublishProductResult(savedProduct.getId().value(), savedProduct.getStatus().name());
    }
}
