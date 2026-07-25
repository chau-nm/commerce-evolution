package dev.chaunm.commerceevolution.cart.presentation.cart.clearcart;

import dev.chaunm.commerceevolution.cart.application.usecase.clearcart.ClearCartUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ClearCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClearCartUseCase clearCartUseCase;

    @Test
    void clearsTheCartAndReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/cart"))
                .andExpect(status().isNoContent());

        verify(clearCartUseCase).clearCart();
    }
}
