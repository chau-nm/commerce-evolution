package dev.chaunm.commerceevolution.catalog.application.usecase.variant.getvariantsnapshot;

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
    private final GetVariantSnapshotMapper getVariantSnapshotMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<VariantSnapshotResult> getByVariantId(UUID variantId) {
        return productRepository.findByVariantId(new VariantId(variantId))
                .map(product -> getVariantSnapshotMapper.toResult(product, product.getVariant(new VariantId(variantId))));
    }
}
