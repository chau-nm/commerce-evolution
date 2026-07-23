package dev.chaunm.commerceevolution.authentication.domain.service;

public interface TokenHasher {
    String hash(String rawToken);
}
