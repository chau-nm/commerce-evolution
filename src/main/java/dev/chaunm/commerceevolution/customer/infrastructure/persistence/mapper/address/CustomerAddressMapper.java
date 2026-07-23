package dev.chaunm.commerceevolution.customer.infrastructure.persistence.mapper.address;

import dev.chaunm.commerceevolution.customer.domain.model.address.Address;
import dev.chaunm.commerceevolution.customer.domain.model.address.valueobject.AddressId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.PhoneNumber;
import dev.chaunm.commerceevolution.customer.infrastructure.persistence.entity.address.CustomerAddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerAddressMapper {

    @Mapping(target = "isDefault", source = "default")
    Address toDomain(CustomerAddressEntity entity);

    CustomerAddressEntity toEntity(Address domain);

    default UUID toUuid(AddressId id) {
        return id == null ? null : id.value();
    }

    default AddressId toAddressId(UUID value) {
        return value == null ? null : new AddressId(value);
    }

    default String toPhoneValue(PhoneNumber phoneNumber) {
        return phoneNumber == null ? null : phoneNumber.value();
    }

    default PhoneNumber toPhoneNumber(String value) {
        return value == null ? null : new PhoneNumber(value);
    }
}
