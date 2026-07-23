package dev.chaunm.commerceevolution.catalog.application.usecase.media.addmedia;

import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.media.MediaView;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddMediaUseCaseImpl implements AddMediaUseCase {

    private final ProductRepository productRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public AddMediaResult addMedia(AddMediaCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        MediaView media = product.addMedia(command.url(), command.primary());

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new AddMediaResult(
                savedProduct.getId().value(),
                media.getId().value(),
                media.getUrl(),
                media.getSortOrder(),
                media.isPrimary()
        );
    }
}
