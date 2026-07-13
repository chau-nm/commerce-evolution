package dev.chaunm.commerceevolution.authentication.presentation.login;

import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginCommand;
import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginMapper;
import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginResult;
import dev.chaunm.commerceevolution.authentication.application.usecase.login.LoginUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginUseCase loginUseCase;
    private final LoginMapper mapper;

    @PostMapping
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResult result = loginUseCase.login(
                mapper.toCommand(request)
        );
        return ResponseEntity.ok(mapper.toResponse(result));
    }

}
