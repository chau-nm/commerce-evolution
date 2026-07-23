package dev.chaunm.commerceevolution.inventory.presentation.reservestock;

import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockUseCase;
import dev.chaunm.commerceevolution.inventory.domain.exception.InsufficientAvailableStockException;
import dev.chaunm.commerceevolution.inventory.domain.exception.InventoryNotFoundException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ReserveStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReserveStockUseCase reserveStockUseCase;

    @MockitoBean
    private ReserveStockMapper mapper;

    @Test
    void reservesStockAndReturnsOk() throws Exception {
        UUID variantId = UUID.randomUUID();
        ReserveStockRequest request = new ReserveStockRequest(4);
        ReserveStockCommand command = new ReserveStockCommand(variantId, 4);
        ReserveStockResult result = new ReserveStockResult(UUID.randomUUID(), variantId, 6, 4);
        ReserveStockResponse response = new ReserveStockResponse(
                result.inventoryId(), result.variantId(), result.availableQuantity(), result.reservedQuantity());

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(reserveStockUseCase.reserve(command)).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/reserve", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableQuantity").value(6))
                .andExpect(jsonPath("$.reservedQuantity").value(4));
    }

    @Test
    void translatesInsufficientAvailableStockExceptionIntoBadRequest() throws Exception {
        UUID variantId = UUID.randomUUID();
        ReserveStockRequest request = new ReserveStockRequest(100);
        ReserveStockCommand command = new ReserveStockCommand(variantId, 100);

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(reserveStockUseCase.reserve(command)).willThrow(new InsufficientAvailableStockException(10, 100));

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/reserve", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("INSUFFICIENT_AVAILABLE_STOCK"));
    }

    @Test
    void translatesInventoryNotFoundExceptionIntoNotFound() throws Exception {
        UUID variantId = UUID.randomUUID();
        ReserveStockRequest request = new ReserveStockRequest(4);
        ReserveStockCommand command = new ReserveStockCommand(variantId, 4);

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(reserveStockUseCase.reserve(command)).willThrow(new InventoryNotFoundException());

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/reserve", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("INVENTORY_NOT_FOUND"));
    }

    @Test
    void rejectsANonPositiveQuantityWithValidationError() throws Exception {
        UUID variantId = UUID.randomUUID();
        ReserveStockRequest request = new ReserveStockRequest(0);

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/reserve", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
