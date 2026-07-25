package dev.chaunm.commerceevolution.shared.testsupport;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

/**
 * Test-only helper mirroring what AuthenticationFilter puts on SecurityContextHolder for a
 * real request, so use-case-level tests can exercise CurrentUserProvider without going through
 * an actual HTTP request/JWT.
 */
public final class TestSecurityContext {

    private TestSecurityContext() {
    }

    public static void authenticateAs(UUID accountId) {
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(accountId, null, List.of()));
    }

    public static void clear() {
        SecurityContextHolder.clearContext();
    }
}
