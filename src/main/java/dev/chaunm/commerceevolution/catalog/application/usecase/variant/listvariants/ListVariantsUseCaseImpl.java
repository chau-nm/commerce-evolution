package dev.chaunm.commerceevolution.catalog.application.usecase.variant.listvariants;

import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListVariantsUseCaseImpl implements ListVariantsUseCase {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public ListVariantsResult list(ListVariantsCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        return new ListVariantsResult(
                product.getVariants().stream()
                        .map(variant -> new ListVariantsResult.VariantItem(
                                variant.getId().value(),
                                variant.getSku().value(),
                                variant.getName(),
                                variant.isActive(),
                                variant.getPrice().amount()
                        ))
                        .toList()
        );
    }
}
