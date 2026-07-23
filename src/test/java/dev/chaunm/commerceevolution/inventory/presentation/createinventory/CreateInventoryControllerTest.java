package dev.chaunm.commerceevolution.inventory.presentation.createinventory;

import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryUseCase;
import dev.chaunm.commerceevolution.inventory.domain.exception.InventoryAlreadyExistsException;
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
class CreateInventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateInventoryUseCase createInventoryUseCase;

    @MockitoBean
    private CreateInventoryMapper mapper;

    @Test
    void createsAnInventoryAndReturnsCreated() throws Exception {
        UUID variantId = UUID.randomUUID();
        CreateInventoryRequest request = new CreateInventoryRequest(variantId, 10);
        CreateInventoryCommand command = new CreateInventoryCommand(variantId, 10);
        CreateInventoryResult result = new CreateInventoryResult(UUID.randomUUID(), variantId, 10, 0);
        CreateInventoryResponse response = new CreateInventoryResponse(
                result.inventoryId(), result.variantId(), result.availableQuantity(), result.reservedQuantity());

        given(mapper.toCommand(request)).willReturn(command);
        given(createInventoryUseCase.create(command)).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(post("/api/v1/inventory/inventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.inventoryId").value(result.inventoryId().toString()))
                .andExpect(jsonPath("$.availableQuantity").value(10));
    }

    @Test
    void translatesInventoryAlreadyExistsExceptionIntoConflict() throws Exception {
        UUID variantId = UUID.randomUUID();
        CreateInventoryRequest request = new CreateInventoryRequest(variantId, 10);
        CreateInventoryCommand command = new CreateInventoryCommand(variantId, 10);

        given(mapper.toCommand(request)).willReturn(command);
        given(createInventoryUseCase.create(command)).willThrow(new InventoryAlreadyExistsException());

        mockMvc.perform(post("/api/v1/inventory/inventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value("INVENTORY_ALREADY_EXISTS"));
    }

    @Test
    void rejectsANegativeInitialQuantityWithValidationError() throws Exception {
        CreateInventoryRequest request = new CreateInventoryRequest(UUID.randomUUID(), -1);

        mockMvc.perform(post("/api/v1/inventory/inventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
