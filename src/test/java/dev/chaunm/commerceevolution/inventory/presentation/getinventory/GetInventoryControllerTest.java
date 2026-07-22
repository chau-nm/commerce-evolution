package dev.chaunm.commerceevolution.inventory.presentation.getinventory;

import dev.chaunm.commerceevolution.inventory.application.usecase.getinventory.GetInventoryCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.getinventory.GetInventoryMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.getinventory.GetInventoryResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.getinventory.GetInventoryUseCase;
import dev.chaunm.commerceevolution.inventory.domain.exception.InventoryNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class GetInventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetInventoryUseCase getInventoryUseCase;

    @MockitoBean
    private GetInventoryMapper mapper;

    @Test
    void returnsTheCurrentStockLevelsForTheVariant() throws Exception {
        UUID variantId = UUID.randomUUID();
        GetInventoryCommand command = new GetInventoryCommand(variantId);
        GetInventoryResult result = new GetInventoryResult(UUID.randomUUID(), variantId, 6, 4);
        GetInventoryResponse response = new GetInventoryResponse(
                result.inventoryId(), result.variantId(), result.availableQuantity(), result.reservedQuantity());

        given(mapper.toCommand(variantId)).willReturn(command);
        given(getInventoryUseCase.getInventory(command)).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(get("/api/v1/inventory/inventories/{variantId}", variantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableQuantity").value(6))
                .andExpect(jsonPath("$.reservedQuantity").value(4));
    }

    @Test
    void translatesInventoryNotFoundExceptionIntoNotFound() throws Exception {
        UUID variantId = UUID.randomUUID();
        GetInventoryCommand command = new GetInventoryCommand(variantId);

        given(mapper.toCommand(variantId)).willReturn(command);
        given(getInventoryUseCase.getInventory(command)).willThrow(new InventoryNotFoundException());

        mockMvc.perform(get("/api/v1/inventory/inventories/{variantId}", variantId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("INVENTORY_NOT_FOUND"));
    }
}
