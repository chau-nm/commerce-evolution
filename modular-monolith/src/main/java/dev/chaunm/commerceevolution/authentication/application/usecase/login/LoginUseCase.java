package dev.chaunm.commerceevolution.authentication.application.usecase.login;

public interface LoginUseCase {
    LoginResult login(LoginCommand command);
}
