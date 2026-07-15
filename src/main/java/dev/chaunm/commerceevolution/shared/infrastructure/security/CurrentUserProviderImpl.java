package dev.chaunm.commerceevolution.shared.infrastructure.security;

import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUser;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Placeholder until AuthenticationFilter is wired into the security chain and populates
 * SecurityContext with the authenticated principal. Replace this with an implementation
 * that reads the account id out of SecurityContextHolder once that lands; nothing in any
 * bounded context's application/presentation layer should need to change when it does.
 */
@Component
public class CurrentUserProviderImpl implements CurrentUserProvider {

    private static final UUID PLACEHOLDER_ACCOUNT_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Override
    public CurrentUser getCurrentUser() {
        return new CurrentUser(PLACEHOLDER_ACCOUNT_ID);
    }
}
