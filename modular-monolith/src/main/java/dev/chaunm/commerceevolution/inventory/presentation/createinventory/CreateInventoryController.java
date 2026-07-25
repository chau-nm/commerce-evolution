package dev.chaunm.commerceevolution.inventory.presentation.createinventory;

import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory/inventories")
@RequiredArgsConstructor
public class CreateInventoryController {

    private final CreateInventoryUseCase createInventoryUseCase;
    private final CreateInventoryMapper mapper;

    @PostMapping
    public ResponseEntity<CreateInventoryResponse> create(
            @Valid @RequestBody CreateInventoryRequest request
    ) {
        CreateInventoryResult result = createInventoryUseCase.create(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
