package dev.chaunm.commerceevolution.authentication.presentation.refreshtoken;

import dev.chaunm.commerceevolution.authentication.application.usecase.refreshtoken.RefreshTokenCommand;
import dev.chaunm.commerceevolution.authentication.application.usecase.refreshtoken.RefreshTokenMapper;
import dev.chaunm.commerceevolution.authentication.application.usecase.refreshtoken.RefreshTokenResult;
import dev.chaunm.commerceevolution.authentication.application.usecase.refreshtoken.RefreshTokenUseCase;
import dev.chaunm.commerceevolution.authentication.domain.exception.InvalidRefreshTokenException;
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
class RefreshTokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RefreshTokenUseCase refreshTokenUseCase;

    @MockitoBean
    private RefreshTokenMapper mapper;

    @Test
    void refreshesAndReturnsNewTokens() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest("old-refresh-token");
        RefreshTokenCommand command = new RefreshTokenCommand("old-refresh-token");
        RefreshTokenResult result = new RefreshTokenResult("new-access-token", "new-refresh-token");
        RefreshTokenResponse response = new RefreshTokenResponse("new-access-token", "new-refresh-token");

        given(mapper.toCommand(request)).willReturn(command);
        given(refreshTokenUseCase.refresh(command)).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(post("/api/v1/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("new-access-token"))
                .andExpect(jsonPath("$.refreshToken").value("new-refresh-token"));
    }

    @Test
    void rejectsABlankRefreshTokenBeforeReachingTheUseCase() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest("");

        mockMvc.perform(post("/api/v1/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(refreshTokenUseCase);
    }

    @Test
    void translatesInvalidRefreshTokenExceptionIntoUnauthorized() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest("old-refresh-token");
        RefreshTokenCommand command = new RefreshTokenCommand("old-refresh-token");

        given(mapper.toCommand(request)).willReturn(command);
        given(refreshTokenUseCase.refresh(command)).willThrow(new InvalidRefreshTokenException());

        mockMvc.perform(post("/api/v1/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorCode").value("INVALID_REFRESH_TOKEN"));
    }
}
