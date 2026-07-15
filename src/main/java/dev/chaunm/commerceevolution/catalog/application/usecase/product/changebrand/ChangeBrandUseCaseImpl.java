package dev.chaunm.commerceevolution.catalog.application.usecase.product.changebrand;

import dev.chaunm.commerceevolution.catalog.domain.exception.brand.BrandNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.repository.brand.BrandRepository;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeBrandUseCaseImpl implements ChangeBrandUseCase {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public ChangeBrandResult changeBrand(ChangeBrandCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        BrandId brandId = new BrandId(command.brandId());
        if (brandRepository.findById(brandId).isEmpty()) {
            throw new BrandNotFoundException();
        }

        product.changeBrand(brandId);

        Product savedProduct = productRepository.save(product);
        product.domainEvents().forEach(domainEventPublisher::publish);

        return new ChangeBrandResult(savedProduct.getId().value(), savedProduct.getBrandId().value());
    }
}
