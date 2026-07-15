package dev.chaunm.commerceevolution.customer.infrastructure.persistence.mapper.customer;

import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.CustomerId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.FullName;
import dev.chaunm.commerceevolution.customer.infrastructure.persistence.entity.customer.CustomerEntity;
import dev.chaunm.commerceevolution.customer.infrastructure.persistence.mapper.address.CustomerAddressMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        uses = {CustomerAddressMapper.class}
)
public interface CustomerMapper {

    Customer toDomain(CustomerEntity entity);

    CustomerEntity toEntity(Customer domain);

    @AfterMapping
    default void linkChildren(@MappingTarget CustomerEntity entity) {
        entity.getAddresses().forEach(address -> address.setCustomer(entity));
    }

    default UUID toUuid(CustomerId id) {
        return id == null ? null : id.value();
    }

    default CustomerId toCustomerId(UUID value) {
        return value == null ? null : new CustomerId(value);
    }

    default UUID toUuid(AccountId id) {
        return id == null ? null : id.value();
    }

    default AccountId toAccountId(UUID value) {
        return value == null ? null : new AccountId(value);
    }

    default String toNameValue(FullName fullName) {
        return fullName == null ? null : fullName.value();
    }

    default FullName toFullName(String value) {
        return value == null ? null : new FullName(value);
    }
}
