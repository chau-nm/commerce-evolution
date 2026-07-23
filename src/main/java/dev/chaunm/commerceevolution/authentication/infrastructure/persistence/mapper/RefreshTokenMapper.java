package dev.chaunm.commerceevolution.authentication.infrastructure.persistence.mapper;

import dev.chaunm.commerceevolution.authentication.domain.model.RefreshToken;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.AccountId;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.RefreshTokenId;
import dev.chaunm.commerceevolution.authentication.infrastructure.persistence.entity.RefreshTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", implementationName = "RefreshTokenEntityMapperImpl")
public interface RefreshTokenMapper {

    @Mapping(target = "accountId", source = "userId")
    RefreshToken toDomain(RefreshTokenEntity entity);

    @Mapping(target = "userId", source = "accountId")
    RefreshTokenEntity toEntity(RefreshToken domain);

    default UUID toUuid(RefreshTokenId id) {
        return id == null ? null : id.value();
    }

    default RefreshTokenId toRefreshTokenId(UUID value) {
        return value == null ? null : new RefreshTokenId(value);
    }

    default UUID toUuid(AccountId id) {
        return id == null ? null : id.value();
    }

    default AccountId toAccountId(UUID value) {
        return value == null ? null : new AccountId(value);
    }
}
