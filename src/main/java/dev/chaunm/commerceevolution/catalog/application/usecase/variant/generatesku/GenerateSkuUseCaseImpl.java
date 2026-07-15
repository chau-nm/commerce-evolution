package dev.chaunm.commerceevolution.catalog.application.usecase.variant.generatesku;

import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.catalog.domain.service.variant.SkuGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenerateSkuUseCaseImpl implements GenerateSkuUseCase {

    private static final int MAX_ATTEMPTS = 5;

    private final ProductRepository productRepository;
    private final SkuGenerator skuGenerator;

    @Override
    @Transactional(readOnly = true)
    public GenerateSkuResult generate(GenerateSkuCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        SKU sku = skuGenerator.generate(product.getName(), command.variantName());
        for (int attempt = 0; attempt < MAX_ATTEMPTS && productRepository.existsByVariantSku(sku); attempt++) {
            sku = skuGenerator.generate(product.getName(), command.variantName());
        }

        return new GenerateSkuResult(sku.value());
    }
}
