package dev.chaunm.commerceevolution.catalog.application.usecase.media.setthumbnail;

import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.media.ProductMedia;
import dev.chaunm.commerceevolution.catalog.domain.model.media.valueobject.MediaId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SetThumbnailUseCaseImpl implements SetThumbnailUseCase {

    private final ProductRepository productRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public SetThumbnailResult setThumbnail(SetThumbnailCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        ProductMedia media = product.markThumbnail(new MediaId(command.mediaId()));

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new SetThumbnailResult(savedProduct.getId().value(), media.getId().value(), media.isPrimary());
    }
}
