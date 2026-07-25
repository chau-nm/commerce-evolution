package dev.chaunm.commerceevolution.catalog.application.usecase.variant.getvariantsnapshot;

import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductStatus;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.VariantView;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.VariantId;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetVariantSnapshotUseCaseImpl implements GetVariantSnapshotUseCase {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<VariantSnapshotResult> getByVariantId(UUID variantId) {
        return productRepository.findByVariantId(new VariantId(variantId))
                .map(product -> toSnapshot(product, product.getVariant(new VariantId(variantId))));
    }

    private VariantSnapshotResult toSnapshot(Product product, VariantView variant) {
        boolean purchasable = product.getStatus() == ProductStatus.PUBLISHED
                && !product.isDeleted()
                && variant.isActive();

        return new VariantSnapshotResult(
                variant.getId().value(),
                product.getId().value(),
                product.getName().value(),
                variant.getName(),
                variant.getPrice().amount(),
                purchasable
        );
    }
}
