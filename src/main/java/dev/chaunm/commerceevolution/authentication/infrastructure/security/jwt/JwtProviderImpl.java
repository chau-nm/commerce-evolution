package dev.chaunm.commerceevolution.authentication.infrastructure.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import dev.chaunm.commerceevolution.authentication.domain.service.JwtProvider;
import dev.chaunm.commerceevolution.authentication.infrastructure.security.exception.FailJwtGenerationException;
import dev.chaunm.commerceevolution.authentication.infrastructure.security.exception.ValidJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProviderImpl implements JwtProvider {

    private final RSAKey rsaKey;
    private final JwtProperties jwtProperties;

    @Override
    public String generateAccessToken(Account account) {
        try {
            Instant now = Instant.now();
            JWSSigner jwsSigner = new RSASSASigner(rsaKey);

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(account.getId().toString())
                    .claim("email", account.getEmail().value())
                    .claim("role", account.getRole().name())
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(now.plus(jwtProperties.accessTokenTtl())))
                    .jwtID(UUID.randomUUID().toString())
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader
                            .Builder(JWSAlgorithm.RS256)
                            .keyID(rsaKey.getKeyID())
                            .build(),
                    claims);

            signedJWT.sign(jwsSigner);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new FailJwtGenerationException(e.getMessage());
        }
    }

    @Override
    public JWTClaimsSet extractClaims(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new RSASSAVerifier(rsaKey);

            if (!signedJWT.verify(verifier)) {
                throw new JOSEException("JWT signature verification failed");
            }

            return signedJWT.getJWTClaimsSet();
        } catch (ParseException | JOSEException e) {
            throw new ValidJwtException(e.getMessage());
        }
    }
}
