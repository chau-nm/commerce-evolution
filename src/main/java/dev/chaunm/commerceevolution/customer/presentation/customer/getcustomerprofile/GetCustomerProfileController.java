package dev.chaunm.commerceevolution.customer.presentation.customer.getcustomerprofile;

import dev.chaunm.commerceevolution.customer.application.usecase.customer.getcustomerprofile.GetCustomerProfileMapper;
import dev.chaunm.commerceevolution.customer.application.usecase.customer.getcustomerprofile.GetCustomerProfileResult;
import dev.chaunm.commerceevolution.customer.application.usecase.customer.getcustomerprofile.GetCustomerProfileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers/me")
@RequiredArgsConstructor
public class GetCustomerProfileController {

    private final GetCustomerProfileUseCase getCustomerProfileUseCase;
    private final GetCustomerProfileMapper mapper;

    @GetMapping
    public ResponseEntity<GetCustomerProfileResponse> getProfile() {
        GetCustomerProfileResult result = getCustomerProfileUseCase.getProfile();
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
