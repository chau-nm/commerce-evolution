package dev.chaunm.commerceevolution.catalog.application.usecase.variant.addvariant;

import dev.chaunm.commerceevolution.catalog.domain.exception.variant.DuplicateVariantSkuException;
import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.VariantView;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.Money;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
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

        Money price = new Money(command.price());
        VariantView variant = product.addVariant(sku, command.name(), price);

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new AddVariantResult(
                savedProduct.getId().value(),
                variant.getId().value(),
                variant.getSku().value(),
                variant.getName(),
                variant.getPrice().amount()
        );
    }
}
