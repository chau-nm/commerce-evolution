package dev.chaunm.commerceevolution.authentication.presentation.login;

import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginCommand;
import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginMapper;
import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginResult;
import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginUseCase;
import dev.chaunm.commerceevolution.authentication.domain.exception.AccountNotFoundException;
import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LoginUseCase loginUseCase;

    @MockitoBean
    private LoginMapper mapper;

    @Test
    void loginsAndReturnsAccessAndRefreshToken() throws Exception {
        LoginRequest request = new LoginRequest("nguyen.van.a@example.com", "Password123");
        LoginCommand command = new LoginCommand("nguyen.van.a@example.com", "Password123");
        LoginResult result = new LoginResult("access-token", "refresh-token");
        LoginResponse response = new LoginResponse("access-token", "refresh-token");

        given(mapper.toCommand(request)).willReturn(command);
        given(loginUseCase.login(command)).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));
    }

    @Test
    void rejectsAnInvalidEmailBeforeReachingTheUseCase() throws Exception {
        LoginRequest request = new LoginRequest("not-an-email", "Password123");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(loginUseCase);
    }

    @Test
    void translatesAccountNotFoundExceptionIntoNotFound() throws Exception {
        LoginRequest request = new LoginRequest("nguyen.van.a@example.com", "Password123");
        LoginCommand command = new LoginCommand("nguyen.van.a@example.com", "Password123");

        given(mapper.toCommand(request)).willReturn(command);
        given(loginUseCase.login(command)).willThrow(new AccountNotFoundException());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("ACCOUNT_NOT_FOUND"));
    }

    @Test
    void translatesInvalidPasswordExceptionIntoBadRequest() throws Exception {
        LoginRequest request = new LoginRequest("nguyen.van.a@example.com", "WrongPassword");
        LoginCommand command = new LoginCommand("nguyen.van.a@example.com", "WrongPassword");

        given(mapper.toCommand(request)).willReturn(command);
        given(loginUseCase.login(command)).willThrow(new InvalidPasswordException());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("INVALID_PASSWORD"));
    }
}
