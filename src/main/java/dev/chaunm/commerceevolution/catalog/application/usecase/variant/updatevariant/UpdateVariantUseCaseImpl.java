package dev.chaunm.commerceevolution.catalog.application.usecase.variant.updatevariant;

import dev.chaunm.commerceevolution.catalog.domain.exception.variant.DuplicateVariantSkuException;
import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.ProductVariant;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.VariantId;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateVariantUseCaseImpl implements UpdateVariantUseCase {

    private final ProductRepository productRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public UpdateVariantResult update(UpdateVariantCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        VariantId variantId = new VariantId(command.variantId());
        SKU sku = new SKU(command.sku());
        if (productRepository.existsByVariantSkuAndIdNot(sku, variantId)) {
            throw new DuplicateVariantSkuException(sku);
        }

        ProductVariant variant = product.updateVariant(variantId, sku, command.name());

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new UpdateVariantResult(
                savedProduct.getId().value(),
                variant.getId().value(),
                variant.getSku().value(),
                variant.getName(),
                variant.isActive()
        );
    }
}
