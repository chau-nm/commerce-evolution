package dev.chaunm.commerceevolution.authentication.domain.service;

import com.nimbusds.jwt.JWTClaimsSet;
import dev.chaunm.commerceevolution.authentication.domain.model.Account;

public interface JwtProvider {
    String generateAccessToken(Account account);

    JWTClaimsSet extractClaims(String token);
}
