package dev.chaunm.commerceevolution.authentication.infrastructure.security.filter;

import com.nimbusds.jwt.JWTClaimsSet;
import dev.chaunm.commerceevolution.authentication.domain.repository.AccountRepository;
import dev.chaunm.commerceevolution.authentication.domain.service.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";

    private final JwtProvider jwtProvider;
    private final AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(BEARER.length());
        JWTClaimsSet claims = jwtProvider.extractClaims(token);
        String email = claims.getClaim("email").toString();


        filterChain.doFilter(request, response);
    }
}
