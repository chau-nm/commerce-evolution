package dev.chaunm.commerceevolution.cart.presentation.cart.getcart;

import dev.chaunm.commerceevolution.cart.application.usecase.getcart.GetCartMapper;
import dev.chaunm.commerceevolution.cart.application.usecase.getcart.GetCartResult;
import dev.chaunm.commerceevolution.cart.application.usecase.getcart.GetCartUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetCartController.class)
@AutoConfigureMockMvc(addFilters = false)
class GetCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetCartUseCase getCartUseCase;

    @MockitoBean
    private GetCartMapper mapper;

    @Test
    void returnsTheCurrentCustomersCart() throws Exception {
        UUID cartId = UUID.randomUUID();
        UUID variantId = UUID.randomUUID();
        GetCartResult result = new GetCartResult(
                cartId, UUID.randomUUID(),
                List.of(new GetCartResult.CartItemResult(UUID.randomUUID(), variantId, 2)),
                Instant.now()
        );
        GetCartResponse response = new GetCartResponse(
                cartId, result.customerId(),
                List.of(new GetCartResponse.CartItemResponse(result.items().getFirst().cartItemId(), variantId, 2)),
                result.updatedAt()
        );

        given(getCartUseCase.getCart()).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(get("/api/v1/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value(cartId.toString()))
                .andExpect(jsonPath("$.items[0].variantId").value(variantId.toString()))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }
}
