package dev.chaunm.commerceevolution.customer.presentation.customer.createcustomer;

import dev.chaunm.commerceevolution.customer.application.usecase.customer.createcustomer.CreateCustomerMapper;
import dev.chaunm.commerceevolution.customer.application.usecase.customer.createcustomer.CreateCustomerResult;
import dev.chaunm.commerceevolution.customer.application.usecase.customer.createcustomer.CreateCustomerUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CreateCustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final CreateCustomerMapper mapper;

    @PostMapping
    public ResponseEntity<CreateCustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CreateCustomerResult result = createCustomerUseCase.create(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
