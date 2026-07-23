package dev.chaunm.commerceevolution.authentication.infrastructure.persistence.mapper;

import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.AccountId;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.HashPassword;
import dev.chaunm.commerceevolution.authentication.infrastructure.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toDomain(AccountEntity entity);

    AccountEntity toEntity(Account domain);

    default UUID toUuid(AccountId id) {
        return id == null ? null : id.value();
    }

    default AccountId toAccountId(UUID value) {
        return value == null ? null : new AccountId(value);
    }

    default String toEmailValue(Email email) {
        return email == null ? null : email.value();
    }

    default Email toEmail(String value) {
        return value == null ? null : new Email(value);
    }

    default String toHashPasswordValue(HashPassword password) {
        return password == null ? null : password.value();
    }

    default HashPassword toHashPassword(String value) {
        return value == null ? null : new HashPassword(value);
    }
}
