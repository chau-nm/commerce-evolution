package dev.chaunm.commerceevolution.inventory.application.usecase.createinventory;

import dev.chaunm.commerceevolution.inventory.domain.exception.InventoryAlreadyExistsException;
import dev.chaunm.commerceevolution.inventory.domain.factory.InventoryFactory;
import dev.chaunm.commerceevolution.inventory.domain.model.Inventory;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.inventory.domain.repository.InventoryRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateInventoryUseCaseImpl implements CreateInventoryUseCase {

    private final InventoryRepository inventoryRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public CreateInventoryResult create(CreateInventoryCommand command) {
        VariantId variantId = new VariantId(command.variantId());
        if (inventoryRepository.existsByVariantId(variantId)) {
            throw new InventoryAlreadyExistsException();
        }

        Inventory inventory = InventoryFactory.create(variantId, command.initialQuantity());

        Inventory savedInventory = inventoryRepository.save(inventory);
        inventory.domainEvents().forEach(domainEventPublisher::publish);

        return new CreateInventoryResult(
                savedInventory.getId().value(),
                savedInventory.getVariantId().value(),
                savedInventory.getAvailableQuantity(),
                savedInventory.getReservedQuantity()
        );
    }
}
