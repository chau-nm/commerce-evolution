package dev.chaunm.commerceevolution.catalog.application.usecase.variant.getvariantsnapshot;

import java.util.Optional;
import java.util.UUID;

/**
 * Public read API other bounded contexts (e.g. order) use to resolve pricing/naming for a
 * variant at a point in time, without depending on {@code catalog.domain.*} directly.
 */
public interface GetVariantSnapshotUseCase {
    Optional<VariantSnapshotResult> getByVariantId(UUID variantId);
}
