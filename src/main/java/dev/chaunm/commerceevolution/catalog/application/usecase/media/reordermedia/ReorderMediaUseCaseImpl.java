package dev.chaunm.commerceevolution.catalog.application.usecase.media.reordermedia;

import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.media.MediaView;
import dev.chaunm.commerceevolution.catalog.domain.model.media.valueobject.MediaId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class ReorderMediaUseCaseImpl implements ReorderMediaUseCase {

    private final ProductRepository productRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public ReorderMediaResult reorder(ReorderMediaCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        product.reorderMedia(command.mediaIds().stream().map(MediaId::new).toList());

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new ReorderMediaResult(
                savedProduct.getId().value(),
                savedProduct.getMedias().stream()
                        .sorted(Comparator.comparingInt(MediaView::getSortOrder))
                        .map(media -> new ReorderMediaResult.MediaItem(
                                media.getId().value(),
                                media.getUrl(),
                                media.getSortOrder(),
                                media.isPrimary()
                        ))
                        .toList()
        );
    }
}
