package dev.chaunm.commerceevolution.cart.presentation.cart.getcart;

import dev.chaunm.commerceevolution.cart.application.usecase.getcart.GetCartMapper;
import dev.chaunm.commerceevolution.cart.application.usecase.getcart.GetCartResult;
import dev.chaunm.commerceevolution.cart.application.usecase.getcart.GetCartUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class GetCartController {

    private final GetCartUseCase getCartUseCase;
    private final GetCartMapper mapper;

    @GetMapping
    public ResponseEntity<GetCartResponse> getCart() {
        GetCartResult result = getCartUseCase.getCart();
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
