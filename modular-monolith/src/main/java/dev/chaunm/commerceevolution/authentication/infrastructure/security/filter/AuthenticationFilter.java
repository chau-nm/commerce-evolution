package dev.chaunm.commerceevolution.authentication.infrastructure.security.filter;

import com.nimbusds.jwt.JWTClaimsSet;
import dev.chaunm.commerceevolution.authentication.domain.service.JwtProvider;
import dev.chaunm.commerceevolution.authentication.infrastructure.security.exception.ValidJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

/**
 * Populates {@link SecurityContextHolder} from a verified access token. The token is
 * self-contained (signature + expiry checked by {@link JwtProvider#extractClaims}), so this
 * never re-queries the account store — one DB round-trip per authenticated request would be
 * wasteful and isn't needed for anything the JWT claims don't already carry.
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(BEARER.length());
        try {
            JWTClaimsSet claims = jwtProvider.extractClaims(token);
            UUID accountId = UUID.fromString(claims.getSubject());
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(claims.getStringClaim("role")));

            var authentication = new UsernamePasswordAuthenticationToken(accountId, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ValidJwtException | ParseException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
