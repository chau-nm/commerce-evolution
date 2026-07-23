package dev.chaunm.commerceevolution.cart.presentation.cart.additem;

import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemCommand;
import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemMapper;
import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemResult;
import dev.chaunm.commerceevolution.cart.application.usecase.additem.AddItemUseCase;
import dev.chaunm.commerceevolution.cart.domain.exception.InvalidQuantityException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AddItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AddItemUseCase addItemUseCase;

    @MockitoBean
    private AddItemMapper mapper;

    @Test
    void addsAnItemAndReturnsCreated() throws Exception {
        UUID variantId = UUID.randomUUID();
        AddItemRequest request = new AddItemRequest(variantId, 2);
        AddItemCommand command = new AddItemCommand(variantId, 2);
        AddItemResult result = new AddItemResult(UUID.randomUUID(), UUID.randomUUID(), variantId, 2);
        AddItemResponse response = new AddItemResponse(result.cartId(), result.cartItemId(), variantId, 2);

        given(mapper.toCommand(request)).willReturn(command);
        given(addItemUseCase.addItem(command)).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(post("/api/v1/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.variantId").value(variantId.toString()))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    void rejectsANonPositiveQuantityBeforeReachingTheUseCase() throws Exception {
        AddItemRequest request = new AddItemRequest(UUID.randomUUID(), 0);

        mockMvc.perform(post("/api/v1/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(addItemUseCase);
    }

    @Test
    void translatesDomainExceptionsIntoBadRequest() throws Exception {
        UUID variantId = UUID.randomUUID();
        AddItemRequest request = new AddItemRequest(variantId, 1);
        AddItemCommand command = new AddItemCommand(variantId, 1);

        given(mapper.toCommand(request)).willReturn(command);
        given(addItemUseCase.addItem(any())).willThrow(new InvalidQuantityException(1));

        mockMvc.perform(post("/api/v1/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("INVALID_QUANTITY"));
    }
}
