package dev.chaunm.commerceevolution.customer.infrastructure.persistence.repository.customer;

import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.CustomerId;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.customer.infrastructure.persistence.mapper.customer.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Customer save(Customer customer) {
        return customerMapper.toDomain(
                jpaCustomerRepository.save(customerMapper.toEntity(customer))
        );
    }

    @Override
    public Optional<Customer> findById(CustomerId id) {
        return jpaCustomerRepository.findById(id.value())
                .map(customerMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByAccountId(AccountId accountId) {
        return jpaCustomerRepository.findByAccountId(accountId.value())
                .map(customerMapper::toDomain);
    }

    @Override
    public boolean existsByAccountId(AccountId accountId) {
        return jpaCustomerRepository.existsByAccountId(accountId.value());
    }
}
