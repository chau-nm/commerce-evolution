package dev.chaunm.commerceevolution.inventory.presentation.adjuststock;

import dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock.AdjustStockCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock.AdjustStockMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock.AdjustStockResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock.AdjustStockUseCase;
import dev.chaunm.commerceevolution.inventory.domain.exception.InvalidQuantityException;
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
class AdjustStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AdjustStockUseCase adjustStockUseCase;

    @MockitoBean
    private AdjustStockMapper mapper;

    @Test
    void adjustsStockAndReturnsOk() throws Exception {
        UUID variantId = UUID.randomUUID();
        AdjustStockRequest request = new AdjustStockRequest(5);
        AdjustStockCommand command = new AdjustStockCommand(variantId, 5);
        AdjustStockResult result = new AdjustStockResult(UUID.randomUUID(), variantId, 15, 0);
        AdjustStockResponse response = new AdjustStockResponse(
                result.inventoryId(), result.variantId(), result.availableQuantity(), result.reservedQuantity());

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(adjustStockUseCase.adjust(command)).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/adjust", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableQuantity").value(15));
    }

    @Test
    void translatesInvalidQuantityExceptionIntoBadRequest() throws Exception {
        UUID variantId = UUID.randomUUID();
        AdjustStockRequest request = new AdjustStockRequest(-100);
        AdjustStockCommand command = new AdjustStockCommand(variantId, -100);

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(adjustStockUseCase.adjust(command)).willThrow(new InvalidQuantityException(-90));

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/adjust", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("INVALID_QUANTITY"));
    }

    @Test
    void translatesInventoryNotFoundExceptionIntoNotFound() throws Exception {
        UUID variantId = UUID.randomUUID();
        AdjustStockRequest request = new AdjustStockRequest(5);
        AdjustStockCommand command = new AdjustStockCommand(variantId, 5);

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(adjustStockUseCase.adjust(command)).willThrow(new InventoryNotFoundException());

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/adjust", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("INVENTORY_NOT_FOUND"));
    }
}
