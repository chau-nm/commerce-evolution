package dev.chaunm.commerceevolution.authentication.presentation.register;

import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterMapper;
import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterResult;
import dev.chaunm.commerceevolution.authentication.application.usecase.register.RegisterUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterUseCase registerUseCase;
    private final RegisterMapper mapper;

    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        RegisterResult result = registerUseCase.register(mapper.toCommand(request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
