package dev.chaunm.commerceevolution.authentication.infrastructure.persistence.mapper;

import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import dev.chaunm.commerceevolution.authentication.infrastructure.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toDomain(AccountEntity entity);
    AccountEntity toEntity(Account domain);
}
