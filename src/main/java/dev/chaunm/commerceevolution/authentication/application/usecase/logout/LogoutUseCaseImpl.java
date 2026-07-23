package dev.chaunm.commerceevolution.authentication.application.usecase.logout;

import dev.chaunm.commerceevolution.authentication.domain.repository.RefreshTokenRepository;
import dev.chaunm.commerceevolution.authentication.domain.service.TokenHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenHasher tokenHasher;

    @Override
    @Transactional
    public void logout(LogoutCommand command) {
        refreshTokenRepository.findByTokenHash(tokenHasher.hash(command.refreshToken()))
                .ifPresent(refreshToken -> {
                    refreshToken.revoke();
                    refreshTokenRepository.save(refreshToken);
                });
    }
}
