package dev.chaunm.commerceevolution.catalog.application.usecase.getvariantdetail;

import dev.chaunm.commerceevolution.catalog.domain.exception.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.ProductVariant;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.catalog.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetVariantDetailUseCaseImpl implements GetVariantDetailUseCase {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public GetVariantDetailResult getDetail(GetVariantDetailCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        ProductVariant variant = product.getVariant(new VariantId(command.variantId()));

        return new GetVariantDetailResult(
                variant.getId().value(),
                product.getId().value(),
                variant.getSku().value(),
                variant.getName(),
                variant.isActive()
        );
    }
}
