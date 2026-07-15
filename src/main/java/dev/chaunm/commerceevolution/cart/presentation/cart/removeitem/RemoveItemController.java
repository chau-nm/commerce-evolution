package dev.chaunm.commerceevolution.cart.presentation.cart.removeitem;

import dev.chaunm.commerceevolution.cart.application.usecase.removeitem.RemoveItemMapper;
import dev.chaunm.commerceevolution.cart.application.usecase.removeitem.RemoveItemUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart/items/{itemId}")
@RequiredArgsConstructor
public class RemoveItemController {

    private final RemoveItemUseCase removeItemUseCase;
    private final RemoveItemMapper mapper;

    @DeleteMapping
    public ResponseEntity<Void> removeItem(@PathVariable UUID itemId) {
        removeItemUseCase.removeItem(mapper.toCommand(itemId));
        return ResponseEntity.noContent().build();
    }
}
