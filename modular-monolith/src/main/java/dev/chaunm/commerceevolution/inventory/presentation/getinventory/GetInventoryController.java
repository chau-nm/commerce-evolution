package dev.chaunm.commerceevolution.inventory.presentation.getinventory;

import dev.chaunm.commerceevolution.inventory.application.usecase.getinventory.GetInventoryMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.getinventory.GetInventoryResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.getinventory.GetInventoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory/inventories/{variantId}")
@RequiredArgsConstructor
public class GetInventoryController {

    private final GetInventoryUseCase getInventoryUseCase;
    private final GetInventoryMapper mapper;

    @GetMapping
    public ResponseEntity<GetInventoryResponse> getInventory(@PathVariable UUID variantId) {
        GetInventoryResult result = getInventoryUseCase.getInventory(mapper.toCommand(variantId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
