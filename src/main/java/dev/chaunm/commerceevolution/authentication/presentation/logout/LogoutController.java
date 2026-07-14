package dev.chaunm.commerceevolution.authentication.presentation.logout;

import dev.chaunm.commerceevolution.authentication.application.usecase.logout.LogoutMapper;
import dev.chaunm.commerceevolution.authentication.application.usecase.logout.LogoutUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/logout")
@RequiredArgsConstructor
public class LogoutController {

    private final LogoutUseCase logoutUseCase;
    private final LogoutMapper mapper;

    @PostMapping
    public ResponseEntity<Void> logout(
            @Valid @RequestBody LogoutRequest request
    ) {
        logoutUseCase.logout(mapper.toCommand(request));
        return ResponseEntity.noContent().build();
    }
}
