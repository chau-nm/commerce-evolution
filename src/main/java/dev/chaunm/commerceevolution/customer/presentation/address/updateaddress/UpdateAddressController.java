package dev.chaunm.commerceevolution.customer.presentation.address.updateaddress;

import dev.chaunm.commerceevolution.customer.application.usecase.address.updateaddress.UpdateAddressMapper;
import dev.chaunm.commerceevolution.customer.application.usecase.address.updateaddress.UpdateAddressResult;
import dev.chaunm.commerceevolution.customer.application.usecase.address.updateaddress.UpdateAddressUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers/me/addresses/{addressId}")
@RequiredArgsConstructor
public class UpdateAddressController {

    private final UpdateAddressUseCase updateAddressUseCase;
    private final UpdateAddressMapper mapper;

    @PutMapping
    public ResponseEntity<UpdateAddressResponse> updateAddress(
            @PathVariable UUID addressId,
            @Valid @RequestBody UpdateAddressRequest request
    ) {
        UpdateAddressResult result = updateAddressUseCase.updateAddress(mapper.toCommand(addressId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
