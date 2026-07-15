package dev.chaunm.commerceevolution.cart.presentation.cart.clearcart;

import dev.chaunm.commerceevolution.cart.application.usecase.clearcart.ClearCartUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class ClearCartController {

    private final ClearCartUseCase clearCartUseCase;

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        clearCartUseCase.clearCart();
        return ResponseEntity.noContent().build();
    }
}
