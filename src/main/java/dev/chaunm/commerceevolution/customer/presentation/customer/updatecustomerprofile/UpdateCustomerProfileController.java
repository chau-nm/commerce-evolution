package dev.chaunm.commerceevolution.customer.presentation.customer.updatecustomerprofile;

import dev.chaunm.commerceevolution.customer.application.usecase.customer.updatecustomerprofile.UpdateCustomerProfileMapper;
import dev.chaunm.commerceevolution.customer.application.usecase.customer.updatecustomerprofile.UpdateCustomerProfileResult;
import dev.chaunm.commerceevolution.customer.application.usecase.customer.updatecustomerprofile.UpdateCustomerProfileUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers/me")
@RequiredArgsConstructor
public class UpdateCustomerProfileController {

    private final UpdateCustomerProfileUseCase updateCustomerProfileUseCase;
    private final UpdateCustomerProfileMapper mapper;

    @PutMapping
    public ResponseEntity<UpdateCustomerProfileResponse> updateProfile(@Valid @RequestBody UpdateCustomerProfileRequest request) {
        UpdateCustomerProfileResult result = updateCustomerProfileUseCase.update(mapper.toCommand(request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
