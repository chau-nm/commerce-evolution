package dev.chaunm.commerceevolution.cart.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.cart.domain.model.Cart;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.cart.domain.repository.CartRepository;
import dev.chaunm.commerceevolution.cart.infrastructure.persistence.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final JpaCartRepository jpaCartRepository;
    private final CartMapper cartMapper;

    @Override
    public Cart save(Cart cart) {
        return cartMapper.toDomain(
                jpaCartRepository.save(cartMapper.toEntity(cart))
        );
    }

    @Override
    public Optional<Cart> findByCustomerId(CustomerId customerId) {
        return jpaCartRepository.findByCustomerId(customerId.value())
                .map(cartMapper::toDomain);
    }
}
