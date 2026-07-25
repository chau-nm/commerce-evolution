package dev.chaunm.commerceevolution.inventory.infrastructure.persistence.mapper;

import dev.chaunm.commerceevolution.inventory.domain.model.Inventory;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.InventoryId;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.inventory.infrastructure.persistence.entity.InventoryEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory toDomain(InventoryEntity entity);

    InventoryEntity toEntity(Inventory domain);

    default UUID toUuid(InventoryId id) {
        return id == null ? null : id.value();
    }

    default InventoryId toInventoryId(UUID value) {
        return value == null ? null : new InventoryId(value);
    }

    default UUID toUuid(VariantId id) {
        return id == null ? null : id.value();
    }

    default VariantId toVariantId(UUID value) {
        return value == null ? null : new VariantId(value);
    }
}
