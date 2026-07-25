package dev.chaunm.commerceevolution.customer.presentation.address.addaddress;

import dev.chaunm.commerceevolution.customer.application.usecase.address.addaddress.AddAddressMapper;
import dev.chaunm.commerceevolution.customer.application.usecase.address.addaddress.AddAddressResult;
import dev.chaunm.commerceevolution.customer.application.usecase.address.addaddress.AddAddressUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers/me/addresses")
@RequiredArgsConstructor
public class AddAddressController {

    private final AddAddressUseCase addAddressUseCase;
    private final AddAddressMapper mapper;

    @PostMapping
    public ResponseEntity<AddAddressResponse> addAddress(@Valid @RequestBody AddAddressRequest request) {
        AddAddressResult result = addAddressUseCase.addAddress(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
