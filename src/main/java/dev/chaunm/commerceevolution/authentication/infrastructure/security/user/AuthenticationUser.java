package dev.chaunm.commerceevolution.authentication.infrastructure.security.user;

import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class AuthenticationUser implements UserDetails {

    private final Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(
                        account.getRole().name()
                )
        );
    }

    @Override
    public @Nullable String getPassword() {
        return account.getPassword().value();
    }

    @Override
    public String getUsername() {
        return account.getEmail().value();
    }
}
