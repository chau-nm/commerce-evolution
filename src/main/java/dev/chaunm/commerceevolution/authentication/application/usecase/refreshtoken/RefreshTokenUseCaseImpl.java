package dev.chaunm.commerceevolution.authentication.application.usecase.refreshtoken;

import dev.chaunm.commerceevolution.authentication.domain.exception.AccountNotFoundException;
import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidRefreshTokenException;
import dev.chaunm.commerceevolution.authentication.domain.factory.RefreshTokenFactory;
import dev.chaunm.commerceevolution.authentication.domain.model.Account;
import dev.chaunm.commerceevolution.authentication.domain.model.RefreshToken;
import dev.chaunm.commerceevolution.authentication.domain.repository.AccountRepository;
import dev.chaunm.commerceevolution.authentication.domain.repository.RefreshTokenRepository;
import dev.chaunm.commerceevolution.authentication.domain.service.JwtProvider;
import dev.chaunm.commerceevolution.authentication.domain.service.RefreshTokenGenerator;
import dev.chaunm.commerceevolution.authentication.domain.service.TokenHasher;
import dev.chaunm.commerceevolution.authentication.infrastructure.security.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AccountRepository accountRepository;
    private final JwtProvider jwtProvider;
    private final TokenHasher tokenHasher;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public RefreshTokenResult refresh(RefreshTokenCommand command) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByTokenHash(tokenHasher.hash(command.refreshToken()))
                .orElseThrow(InvalidRefreshTokenException::new);

        if (!refreshToken.isActive(Instant.now())) {
            throw new InvalidRefreshTokenException();
        }

        Account account = accountRepository.findById(refreshToken.getAccountId())
                .orElseThrow(AccountNotFoundException::new);

        refreshToken.revoke();
        refreshTokenRepository.save(refreshToken);

        String newRawRefreshToken = refreshTokenGenerator.generate();
        RefreshToken newRefreshToken = RefreshTokenFactory.create(
                account.getId(),
                tokenHasher.hash(newRawRefreshToken),
                jwtProperties.refreshTokenTtl()
        );
        refreshTokenRepository.save(newRefreshToken);

        return new RefreshTokenResult(
                jwtProvider.generateAccessToken(account),
                newRawRefreshToken
        );
    }
}
