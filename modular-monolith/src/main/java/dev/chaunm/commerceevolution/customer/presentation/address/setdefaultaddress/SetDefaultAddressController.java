package dev.chaunm.commerceevolution.customer.presentation.address.setdefaultaddress;

import dev.chaunm.commerceevolution.customer.application.usecase.address.setdefaultaddress.SetDefaultAddressMapper;
import dev.chaunm.commerceevolution.customer.application.usecase.address.setdefaultaddress.SetDefaultAddressResult;
import dev.chaunm.commerceevolution.customer.application.usecase.address.setdefaultaddress.SetDefaultAddressUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers/me/addresses/{addressId}/default")
@RequiredArgsConstructor
public class SetDefaultAddressController {

    private final SetDefaultAddressUseCase setDefaultAddressUseCase;
    private final SetDefaultAddressMapper mapper;

    @PutMapping
    public ResponseEntity<SetDefaultAddressResponse> setDefault(@PathVariable UUID addressId) {
        SetDefaultAddressResult result = setDefaultAddressUseCase.setDefault(mapper.toCommand(addressId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
