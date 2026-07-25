package dev.chaunm.paymentservice.shared.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

/**
 * Payment-service has no end-user auth of its own — every inbound call is either order-service
 * initiating a payment or (in a real integration) a gateway webhook. This filter is the minimum
 * viable gate: reject anything that doesn't present the shared internal API key. Actuator and
 * OpenAPI/Swagger routes stay open since they carry no business data.
 */
@Component
public class InternalApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-Internal-Api-Key";
    private static final Set<String> OPEN_PATH_PREFIXES = Set.of(
            "/actuator", "/v3/api-docs", "/swagger-ui");

    private final InternalApiKeyProperties properties;

    public InternalApiKeyFilter(InternalApiKeyProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (OPEN_PATH_PREFIXES.stream().anyMatch(path::startsWith)) {
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
}
