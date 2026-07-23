package dev.chaunm.commerceevolution.inventory.application.usecase.getinventory;

import dev.chaunm.commerceevolution.inventory.domain.exception.InventoryNotFoundException;
import dev.chaunm.commerceevolution.inventory.domain.model.Inventory;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.inventory.domain.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetInventoryUseCaseImpl implements GetInventoryUseCase {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public GetInventoryResult getInventory(GetInventoryCommand command) {
        Inventory inventory = inventoryRepository.findByVariantId(new VariantId(command.variantId()))
                .orElseThrow(InventoryNotFoundException::new);

        return new GetInventoryResult(
                inventory.getId().value(),
                inventory.getVariantId().value(),
                inventory.getAvailableQuantity(),
                inventory.getReservedQuantity()
        );
    }
}
