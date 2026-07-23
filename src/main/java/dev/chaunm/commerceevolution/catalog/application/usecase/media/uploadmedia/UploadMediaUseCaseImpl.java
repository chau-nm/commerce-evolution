package dev.chaunm.commerceevolution.catalog.application.usecase.media.uploadmedia;

import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.media.MediaView;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.catalog.domain.service.media.MediaStorage;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UploadMediaUseCaseImpl implements UploadMediaUseCase {

    private final ProductRepository productRepository;
    private final MediaStorage mediaStorage;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public UploadMediaResult upload(UploadMediaCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        String url = mediaStorage.store(command.filename(), command.content(), command.contentType());
        MediaView media = product.addMedia(url, command.primary());

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new UploadMediaResult(
                savedProduct.getId().value(),
                media.getId().value(),
                media.getUrl(),
                media.getSortOrder(),
                media.isPrimary()
        );
    }
}
