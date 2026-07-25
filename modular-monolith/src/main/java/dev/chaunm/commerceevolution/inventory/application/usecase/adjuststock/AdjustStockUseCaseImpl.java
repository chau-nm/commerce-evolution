package dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock;

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
public class AdjustStockUseCaseImpl implements AdjustStockUseCase {

    private final InventoryRepository inventoryRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public AdjustStockResult adjust(AdjustStockCommand command) {
        Inventory inventory = inventoryRepository.findByVariantId(new VariantId(command.variantId()))
                .orElseThrow(InventoryNotFoundException::new);

        inventory.adjustStock(command.quantityDelta());

        Inventory savedInventory = inventoryRepository.save(inventory);
        inventory.domainEvents().forEach(domainEventPublisher::publish);

        return new AdjustStockResult(
                savedInventory.getId().value(),
                savedInventory.getVariantId().value(),
                savedInventory.getAvailableQuantity(),
                savedInventory.getReservedQuantity()
        );
    }
}
