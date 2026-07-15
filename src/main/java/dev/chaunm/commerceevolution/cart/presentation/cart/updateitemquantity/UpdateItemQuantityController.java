package dev.chaunm.commerceevolution.cart.presentation.cart.updateitemquantity;

import dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity.UpdateItemQuantityMapper;
import dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity.UpdateItemQuantityResult;
import dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity.UpdateItemQuantityUseCase;
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
@RequestMapping("/api/v1/cart/items/{itemId}")
@RequiredArgsConstructor
public class UpdateItemQuantityController {

    private final UpdateItemQuantityUseCase updateItemQuantityUseCase;
    private final UpdateItemQuantityMapper mapper;

    @PutMapping
    public ResponseEntity<UpdateItemQuantityResponse> updateQuantity(
            @PathVariable UUID itemId,
            @Valid @RequestBody UpdateItemQuantityRequest request
    ) {
        UpdateItemQuantityResult result = updateItemQuantityUseCase.updateQuantity(mapper.toCommand(itemId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
