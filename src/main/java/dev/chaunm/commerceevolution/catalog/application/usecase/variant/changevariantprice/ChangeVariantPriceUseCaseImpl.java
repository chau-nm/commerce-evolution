package dev.chaunm.commerceevolution.catalog.application.usecase.variant.changevariantprice;

import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.ProductVariant;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.Money;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.VariantId;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeVariantPriceUseCaseImpl implements ChangeVariantPriceUseCase {

    private final ProductRepository productRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public ChangeVariantPriceResult changePrice(ChangeVariantPriceCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        Money price = new Money(command.price());
        ProductVariant variant = product.changeVariantPrice(new VariantId(command.variantId()), price);

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new ChangeVariantPriceResult(
                savedProduct.getId().value(),
                variant.getId().value(),
                variant.getSku().value(),
                variant.getName(),
                variant.getPrice().amount()
        );
    }
}
