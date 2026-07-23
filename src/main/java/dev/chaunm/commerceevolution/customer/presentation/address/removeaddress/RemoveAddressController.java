package dev.chaunm.commerceevolution.customer.presentation.address.removeaddress;

import dev.chaunm.commerceevolution.customer.application.usecase.address.removeaddress.RemoveAddressMapper;
import dev.chaunm.commerceevolution.customer.application.usecase.address.removeaddress.RemoveAddressUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers/me/addresses/{addressId}")
@RequiredArgsConstructor
public class RemoveAddressController {

    private final RemoveAddressUseCase removeAddressUseCase;
    private final RemoveAddressMapper mapper;

    @DeleteMapping
    public ResponseEntity<Void> removeAddress(@PathVariable UUID addressId) {
        removeAddressUseCase.removeAddress(mapper.toCommand(addressId));
        return ResponseEntity.noContent().build();
    }
}
