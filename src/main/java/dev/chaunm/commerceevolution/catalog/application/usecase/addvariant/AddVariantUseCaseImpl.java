package dev.chaunm.commerceevolution.catalog.application.usecase.addvariant;

import dev.chaunm.commerceevolution.catalog.domain.exception.DuplicateVariantSkuException;
import dev.chaunm.commerceevolution.catalog.domain.exception.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.ProductVariant;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.repository.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddVariantUseCaseImpl implements AddVariantUseCase {

    private final ProductRepository productRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public AddVariantResult addVariant(AddVariantCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        SKU sku = new SKU(command.sku());
        if (productRepository.existsByVariantSku(sku)) {
            throw new DuplicateVariantSkuException(sku);
        }

        ProductVariant variant = product.addVariant(sku, command.name());

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new AddVariantResult(
                savedProduct.getId().value(),
                variant.getId().value(),
                variant.getSku().value(),
                variant.getName()
        );
    }
}
