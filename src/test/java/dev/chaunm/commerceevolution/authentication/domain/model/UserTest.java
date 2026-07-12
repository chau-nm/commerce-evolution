package dev.chaunm.commerceevolution.authentication.domain.model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    /** A deliberately trivial fake: the domain must not care which hashing algorithm is used. */
    private static final PasswordEncoder PLAINTEXT_ENCODER = new PasswordEncoder() {
        @Override
        public String encode(String rawPassword) {
            return "hashed:" + rawPassword;
        }

        @Override
        public boolean matches(String rawPassword, String hashedPassword) {
            return Objects.equals("hashed:" + rawPassword, hashedPassword);
        }
    };

    @Test
    void registerAssignsCustomerRoleAndRaisesEvent() {
        Email email = new Email("new.user@example.com");
        HashedPassword hashedPassword = new HashedPassword(PLAINTEXT_ENCODER.encode("s3cret-pass"));

        User user = User.register(email, hashedPassword);

        assertThat(user.getRoles()).containsExactly(Role.CUSTOMER);
        assertThat(user.isActive()).isTrue();
        assertThat(user.pullDomainEvents())
                .singleElement()
                .isInstanceOf(UserRegisteredEvent.class)
                .satisfies(event -> {
                    UserRegisteredEvent registered = (UserRegisteredEvent) event;
                    assertThat(registered.userId()).isEqualTo(user.getId());
                    assertThat(registered.email()).isEqualTo(email);
                });
    }

    @Test
    void pullDomainEventsClearsThemAfterwards() {
        User user = User.register(new Email("once@example.com"), new HashedPassword("hash"));

        user.pullDomainEvents();

        assertThat(user.pullDomainEvents()).isEmpty();
    }

    @Test
    void matchesPasswordDelegatesToPasswordEncoder() {
        User user = User.register(new Email("login@example.com"), new HashedPassword(PLAINTEXT_ENCODER.encode("correct-horse")));

        assertThat(user.matchesPassword("correct-horse", PLAINTEXT_ENCODER)).isTrue();
        assertThat(user.matchesPassword("wrong-password", PLAINTEXT_ENCODER)).isFalse();
    }

    @Test
    void disabledUserNeverMatchesPassword() {
        User user = User.reconstitute(
                UserId.generate(),
                new Email("disabled@example.com"),
                new HashedPassword(PLAINTEXT_ENCODER.encode("correct-horse")),
                java.util.Set.of(Role.CUSTOMER),
                UserStatus.DISABLED,
                java.time.Instant.now());

        assertThat(user.matchesPassword("correct-horse", PLAINTEXT_ENCODER)).isFalse();
    }
}
