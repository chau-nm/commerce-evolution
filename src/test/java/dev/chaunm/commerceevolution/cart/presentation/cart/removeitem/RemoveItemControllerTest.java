package dev.chaunm.commerceevolution.cart.presentation.cart.removeitem;

import dev.chaunm.commerceevolution.cart.application.usecase.removeitem.RemoveItemCommand;
import dev.chaunm.commerceevolution.cart.application.usecase.removeitem.RemoveItemMapper;
import dev.chaunm.commerceevolution.cart.application.usecase.removeitem.RemoveItemUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RemoveItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RemoveItemUseCase removeItemUseCase;

    @MockitoBean
    private RemoveItemMapper mapper;

    @Test
    void removesTheItemAndReturnsNoContent() throws Exception {
        UUID itemId = UUID.randomUUID();
        RemoveItemCommand command = new RemoveItemCommand(itemId);
        given(mapper.toCommand(itemId)).willReturn(command);

        mockMvc.perform(delete("/api/v1/cart/items/{itemId}", itemId))
                .andExpect(status().isNoContent());

        verify(removeItemUseCase).removeItem(command);
    }
}
