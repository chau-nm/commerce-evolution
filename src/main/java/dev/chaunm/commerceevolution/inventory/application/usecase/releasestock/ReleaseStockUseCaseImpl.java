package dev.chaunm.commerceevolution.inventory.application.usecase.releasestock;

import dev.chaunm.commerceevolution.inventory.domain.exception.InventoryNotFoundException;
import dev.chaunm.commerceevolution.inventory.domain.model.Inventory;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.inventory.domain.repository.InventoryRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReleaseStockUseCaseImpl implements ReleaseStockUseCase {

    private final InventoryRepository inventoryRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public ReleaseStockResult release(ReleaseStockCommand command) {
        Inventory inventory = inventoryRepository.findByVariantId(new VariantId(command.variantId()))
                .orElseThrow(InventoryNotFoundException::new);

        inventory.release(command.quantity());

        Inventory savedInventory = inventoryRepository.save(inventory);
        inventory.domainEvents().forEach(domainEventPublisher::publish);

        return new ReleaseStockResult(
                savedInventory.getId().value(),
                savedInventory.getVariantId().value(),
                savedInventory.getAvailableQuantity(),
                savedInventory.getReservedQuantity()
        );
    }
}
