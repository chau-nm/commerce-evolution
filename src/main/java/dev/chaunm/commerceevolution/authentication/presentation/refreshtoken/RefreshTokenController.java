package dev.chaunm.commerceevolution.authentication.presentation.refreshtoken;

import dev.chaunm.commerceevolution.authentication.application.usecase.refreshtoken.RefreshTokenMapper;
import dev.chaunm.commerceevolution.authentication.application.usecase.refreshtoken.RefreshTokenResult;
import dev.chaunm.commerceevolution.authentication.application.usecase.refreshtoken.RefreshTokenUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/refresh-token")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenUseCase refreshTokenUseCase;
    private final RefreshTokenMapper mapper;

    @PostMapping
    public ResponseEntity<RefreshTokenResponse> refresh(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        RefreshTokenResult result = refreshTokenUseCase.refresh(mapper.toCommand(request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
