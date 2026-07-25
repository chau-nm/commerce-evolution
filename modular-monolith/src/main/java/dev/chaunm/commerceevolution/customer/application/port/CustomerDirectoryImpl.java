package dev.chaunm.commerceevolution.customer.application.port;

import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class CustomerDirectoryImpl implements CustomerDirectory {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<UUID> findCustomerIdByAccountId(UUID accountId) {
        return customerRepository.findByAccountId(new AccountId(accountId))
                .map(customer -> customer.getId().value());
    }
}
