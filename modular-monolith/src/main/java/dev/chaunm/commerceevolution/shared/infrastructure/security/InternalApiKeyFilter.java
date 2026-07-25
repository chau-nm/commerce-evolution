package dev.chaunm.commerceevolution.shared.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Gates the small set of endpoints that exist purely for service-to-service calls from
 * payment-service (never an end user): the payment-outcome webhook and the mark-order-paid
 * endpoint. Every other path is left untouched here and continues to be governed by the normal
 * JWT-based {@code SecurityConfiguration} chain.
 */
@Component
@RequiredArgsConstructor
public class InternalApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-Internal-Api-Key";
    private static final Pattern MARK_PAID_PATH = Pattern.compile("^/api/v1/orders/[^/]+/pay$");

    private final InternalApiKeyProperties properties;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (!isInternalOnlyEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String presentedKey = request.getHeader(API_KEY_HEADER);
        if (presentedKey == null || !presentedKey.equals(properties.apiKey())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
            response.getWriter().write(
                    "{\"title\":\"Unauthorized\",\"status\":401,\"detail\":\"Missing or invalid " + API_KEY_HEADER + "\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isInternalOnlyEndpoint(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path.startsWith("/internal/")) {
            return true;
        }
        return "PUT".equalsIgnoreCase(request.getMethod()) && MARK_PAID_PATH.matcher(path).matches();
    }
}
