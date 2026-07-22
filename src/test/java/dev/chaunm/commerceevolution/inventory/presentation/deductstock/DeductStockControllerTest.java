package dev.chaunm.commerceevolution.inventory.presentation.deductstock;

import dev.chaunm.commerceevolution.inventory.application.usecase.deductstock.DeductStockCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.deductstock.DeductStockMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.deductstock.DeductStockResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.deductstock.DeductStockUseCase;
import dev.chaunm.commerceevolution.inventory.domain.exception.InsufficientReservedStockException;
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
class DeductStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DeductStockUseCase deductStockUseCase;

    @MockitoBean
    private DeductStockMapper mapper;

    @Test
    void deductsStockAndReturnsOk() throws Exception {
        UUID variantId = UUID.randomUUID();
        DeductStockRequest request = new DeductStockRequest(4);
        DeductStockCommand command = new DeductStockCommand(variantId, 4);
        DeductStockResult result = new DeductStockResult(UUID.randomUUID(), variantId, 4, 2);
        DeductStockResponse response = new DeductStockResponse(
                result.inventoryId(), result.variantId(), result.availableQuantity(), result.reservedQuantity());

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(deductStockUseCase.deduct(command)).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/deduct", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservedQuantity").value(2));
    }

    @Test
    void translatesInsufficientReservedStockExceptionIntoBadRequest() throws Exception {
        UUID variantId = UUID.randomUUID();
        DeductStockRequest request = new DeductStockRequest(100);
        DeductStockCommand command = new DeductStockCommand(variantId, 100);

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(deductStockUseCase.deduct(command)).willThrow(new InsufficientReservedStockException(3, 100));

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/deduct", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("INSUFFICIENT_RESERVED_STOCK"));
    }

    @Test
    void translatesInventoryNotFoundExceptionIntoNotFound() throws Exception {
        UUID variantId = UUID.randomUUID();
        DeductStockRequest request = new DeductStockRequest(4);
        DeductStockCommand command = new DeductStockCommand(variantId, 4);

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(deductStockUseCase.deduct(command)).willThrow(new InventoryNotFoundException());

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/deduct", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("INVENTORY_NOT_FOUND"));
    }

    @Test
    void rejectsANonPositiveQuantityWithValidationError() throws Exception {
        UUID variantId = UUID.randomUUID();
        DeductStockRequest request = new DeductStockRequest(0);

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/deduct", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
