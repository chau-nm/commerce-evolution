package dev.chaunm.commerceevolution.authentication.presentation.register;

import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterCommand;
import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterMapper;
import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterResult;
import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterUseCase;
import dev.chaunm.commerceevolution.authentication.domain.exception.ExistedEmailException;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Email;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegisterUseCase registerUseCase;

    @MockitoBean
    private RegisterMapper mapper;

    @Test
    void registersAnAccountAndReturnsOk() throws Exception {
        RegisterRequest request = new RegisterRequest("nguyen.van.a@example.com", "Password123");
        RegisterCommand command = new RegisterCommand("nguyen.van.a@example.com", "Password123");
        RegisterResult result = new RegisterResult(UUID.randomUUID());
        RegisterResponse response = new RegisterResponse(result.id());

        given(mapper.toCommand(request)).willReturn(command);
        given(registerUseCase.register(command)).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(result.id().toString()));
    }

    @Test
    void translatesExistedEmailExceptionIntoConflict() throws Exception {
        RegisterRequest request = new RegisterRequest("nguyen.van.a@example.com", "Password123");
        RegisterCommand command = new RegisterCommand("nguyen.van.a@example.com", "Password123");

        given(mapper.toCommand(request)).willReturn(command);
        given(registerUseCase.register(command))
                .willThrow(new ExistedEmailException(new Email("nguyen.van.a@example.com")));

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value("EXISTED_EMAIL"));
    }
}
