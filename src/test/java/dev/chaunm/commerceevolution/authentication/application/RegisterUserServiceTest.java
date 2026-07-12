package dev.chaunm.commerceevolution.authentication.application;

import dev.chaunm.commerceevolution.authentication.application.command.RegisterUserCommand;
import dev.chaunm.commerceevolution.authentication.application.port.out.DomainEventPublisher;
import dev.chaunm.commerceevolution.authentication.application.result.RegisterUserResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private DomainEventPublisher domainEventPublisher;

    private RegisterUserService registerUserService;

    @BeforeEach
    void setUp() {
        registerUserService = new RegisterUserService(userRepository, passwordEncoder, domainEventPublisher);
    }

    @Test
    void registersNewUserAndPublishesEvent() {
        RegisterUserCommand command = new RegisterUserCommand("new.user@example.com", "s3cret-pass");
        when(userRepository.existsByEmail(new Email("new.user@example.com"))).thenReturn(false);
        when(passwordEncoder.encode("s3cret-pass")).thenReturn("hashed-password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RegisterUserResult result = registerUserService.register(command);

        assertThat(result.email()).isEqualTo("new.user@example.com");

        ArgumentCaptor<UserRegisteredEvent> eventCaptor = ArgumentCaptor.forClass(UserRegisteredEvent.class);
        verify(domainEventPublisher).publish(eventCaptor.capture());
        assertThat(eventCaptor.getValue().email()).isEqualTo(new Email("new.user@example.com"));
    }

    @Test
    void rejectsDuplicateEmailWithoutTouchingPasswordOrPublisher() {
        RegisterUserCommand command = new RegisterUserCommand("taken@example.com", "s3cret-pass");
        when(userRepository.existsByEmail(new Email("taken@example.com"))).thenReturn(true);

        assertThatThrownBy(() -> registerUserService.register(command))
                .isInstanceOf(EmailAlreadyExistsException.class);

        verifyNoInteractions(passwordEncoder, domainEventPublisher);
    }
}
