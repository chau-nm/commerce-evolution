package dev.chaunm.commerceevolution.cart.presentation.cart.additem;

import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemMapper;
import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemResult;
import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart/items")
@RequiredArgsConstructor
public class AddItemController {

    private final AddItemUseCase addItemUseCase;
    private final AddItemMapper mapper;

    @PostMapping
    public ResponseEntity<AddItemResponse> addItem(@Valid @RequestBody AddItemRequest request) {
        AddItemResult result = addItemUseCase.addItem(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
