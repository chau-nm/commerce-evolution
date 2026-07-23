package dev.chaunm.commerceevolution.authentication.presentation.logout;

import dev.chaunm.commerceevolution.authentication.application.usecase.logout.LogoutCommand;
import dev.chaunm.commerceevolution.authentication.application.usecase.logout.LogoutMapper;
import dev.chaunm.commerceevolution.authentication.application.usecase.logout.LogoutUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class LogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LogoutUseCase logoutUseCase;

    @MockitoBean
    private LogoutMapper mapper;

    @Test
    void logsOutAndReturnsNoContent() throws Exception {
        LogoutRequest request = new LogoutRequest("refresh-token");
        LogoutCommand command = new LogoutCommand("refresh-token");

        given(mapper.toCommand(request)).willReturn(command);

        mockMvc.perform(post("/api/v1/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(logoutUseCase).logout(command);
    }

    @Test
    void rejectsABlankRefreshTokenBeforeReachingTheUseCase() throws Exception {
        LogoutRequest request = new LogoutRequest("");

        mockMvc.perform(post("/api/v1/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(logoutUseCase);
    }
}
