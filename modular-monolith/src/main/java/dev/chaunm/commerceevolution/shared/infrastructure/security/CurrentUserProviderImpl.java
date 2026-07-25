package dev.chaunm.commerceevolution.shared.infrastructure.security;

import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUser;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.domain.exception.UnauthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

/** Reads the authenticated principal populated by AuthenticationFilter into SecurityContext. */
@Component
public class CurrentUserProviderImpl implements CurrentUserProvider {

    @Override
    public CurrentUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UUID accountId)) {
            throw new UnauthenticatedException();
        }
        return new CurrentUser(accountId);
    }
}
