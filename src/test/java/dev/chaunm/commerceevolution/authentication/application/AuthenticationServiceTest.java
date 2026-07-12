package dev.chaunm.commerceevolution.authentication.application;

import dev.chaunm.commerceevolution.authentication.application.command.LoginCommand;
import dev.chaunm.commerceevolution.authentication.application.command.LogoutCommand;
import dev.chaunm.commerceevolution.authentication.application.command.RefreshTokenCommand;
import dev.chaunm.commerceevolution.authentication.application.port.out.TokenProvider;
import dev.chaunm.commerceevolution.authentication.application.result.AuthTokens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    private AuthenticationService authenticationService;
    private User activeUser;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(
                userRepository, refreshTokenRepository, passwordEncoder, tokenProvider, Duration.ofDays(30));

        activeUser = User.reconstitute(
                UserId.generate(),
                new Email("user@example.com"),
                new HashedPassword("hashed-password"),
                Set.of(Role.CUSTOMER),
                UserStatus.ACTIVE,
                Instant.now());

        lenient().when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        lenient().when(tokenProvider.generateAccessToken(any(User.class))).thenReturn("access-token");
        lenient().when(tokenProvider.accessTokenTimeToLive()).thenReturn(Duration.ofMinutes(15));
    }

    @Test
    void loginIssuesAccessAndRefreshTokensOnCorrectCredentials() {
        when(userRepository.findByEmail(activeUser.getEmail())).thenReturn(Optional.of(activeUser));
        when(passwordEncoder.matches("correct-password", "hashed-password")).thenReturn(true);

        AuthTokens tokens = authenticationService.login(new LoginCommand("user@example.com", "correct-password"));

        assertThat(tokens.accessToken()).isEqualTo("access-token");
        assertThat(tokens.refreshToken()).isNotBlank();
        assertThat(tokens.expiresInSeconds()).isEqualTo(900);
    }

    @Test
    void loginRejectsWrongPassword() {
        when(userRepository.findByEmail(activeUser.getEmail())).thenReturn(Optional.of(activeUser));
        when(passwordEncoder.matches("wrong-password", "hashed-password")).thenReturn(false);

        assertThatThrownBy(() -> authenticationService.login(new LoginCommand("user@example.com", "wrong-password")))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void loginRejectsUnknownEmailWithoutLeakingWhichPartWasWrong() {
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authenticationService.login(new LoginCommand("nobody@example.com", "whatever")))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void refreshRotatesTokenAndRevokesThePresentedOne() {
        RefreshToken presentedToken = RefreshToken.issue(activeUser.getId(), "irrelevant-hash", Duration.ofDays(30));
        when(refreshTokenRepository.findByTokenHash(any())).thenReturn(Optional.of(presentedToken));
        when(userRepository.findById(activeUser.getId())).thenReturn(Optional.of(activeUser));

        AuthTokens tokens = authenticationService.refresh(new RefreshTokenCommand("raw-refresh-token"));

        assertThat(tokens.accessToken()).isEqualTo("access-token");
        assertThat(presentedToken.isRevoked()).isTrue();

        ArgumentCaptor<RefreshToken> savedCaptor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(refreshTokenRepository, org.mockito.Mockito.times(2)).save(savedCaptor.capture());
        assertThat(savedCaptor.getAllValues()).anySatisfy(saved -> assertThat(saved.isRevoked()).isTrue());
    }

    @Test
    void refreshRejectsExpiredToken() {
        RefreshToken expiredToken = RefreshToken.reconstitute(
                dev.chaunm.commerceevolution.authentication.domain.model.RefreshTokenId.generate(),
                activeUser.getId(),
                "hash",
                Instant.now().minusSeconds(60),
                false,
                Instant.now().minusSeconds(120));
        when(refreshTokenRepository.findByTokenHash(any())).thenReturn(Optional.of(expiredToken));

        assertThatThrownBy(() -> authenticationService.refresh(new RefreshTokenCommand("raw-refresh-token")))
                .isInstanceOf(InvalidRefreshTokenException.class);
    }

    @Test
    void refreshRejectsUnknownToken() {
        when(refreshTokenRepository.findByTokenHash(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authenticationService.refresh(new RefreshTokenCommand("does-not-exist")))
                .isInstanceOf(InvalidRefreshTokenException.class);
    }

    @Test
    void logoutRevokesMatchingToken() {
        RefreshToken presentedToken = RefreshToken.issue(activeUser.getId(), "irrelevant-hash", Duration.ofDays(30));
        when(refreshTokenRepository.findByTokenHash(any())).thenReturn(Optional.of(presentedToken));

        authenticationService.logout(new LogoutCommand("raw-refresh-token"));

        assertThat(presentedToken.isRevoked()).isTrue();
        verify(refreshTokenRepository).save(presentedToken);
    }

    @Test
    void logoutIsIdempotentWhenTokenIsAlreadyGone() {
        when(refreshTokenRepository.findByTokenHash(any())).thenReturn(Optional.empty());

        authenticationService.logout(new LogoutCommand("does-not-exist"));

        verify(refreshTokenRepository, never()).save(any());
    }
}
