package dev.chaunm.commerceevolution.cart.presentation.cart.updateitemquantity;

import dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity.UpdateItemQuantityCommand;
import dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity.UpdateItemQuantityMapper;
import dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity.UpdateItemQuantityResult;
import dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity.UpdateItemQuantityUseCase;
import dev.chaunm.commerceevolution.cart.domain.exception.CartItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UpdateItemQuantityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UpdateItemQuantityUseCase updateItemQuantityUseCase;

    @MockitoBean
    private UpdateItemQuantityMapper mapper;

    @Test
    void updatesTheItemQuantity() throws Exception {
        UUID itemId = UUID.randomUUID();
        UpdateItemQuantityRequest request = new UpdateItemQuantityRequest(5);
        UpdateItemQuantityCommand command = new UpdateItemQuantityCommand(itemId, 5);
        UpdateItemQuantityResult result = new UpdateItemQuantityResult(UUID.randomUUID(), itemId, UUID.randomUUID(), 5, false);
        UpdateItemQuantityResponse response = new UpdateItemQuantityResponse(result.cartId(), itemId, result.variantId(), 5, false);

        given(mapper.toCommand(itemId, request)).willReturn(command);
        given(updateItemQuantityUseCase.updateQuantity(command)).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(put("/api/v1/cart/items/{itemId}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.removed").value(false));
    }

    @Test
    void translatesCartItemNotFoundIntoNotFound() throws Exception {
        UUID itemId = UUID.randomUUID();
        UpdateItemQuantityRequest request = new UpdateItemQuantityRequest(5);
        UpdateItemQuantityCommand command = new UpdateItemQuantityCommand(itemId, 5);

        given(mapper.toCommand(itemId, request)).willReturn(command);
        given(updateItemQuantityUseCase.updateQuantity(command)).willThrow(new CartItemNotFoundException());

        mockMvc.perform(put("/api/v1/cart/items/{itemId}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("CART_ITEM_NOT_FOUND"));
    }
}
