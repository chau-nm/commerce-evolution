package dev.chaunm.commerceevolution.inventory.presentation.releasestock;

import dev.chaunm.commerceevolution.inventory.application.usecase.releasestock.ReleaseStockCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.releasestock.ReleaseStockMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.releasestock.ReleaseStockResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.releasestock.ReleaseStockUseCase;
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
class ReleaseStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReleaseStockUseCase releaseStockUseCase;

    @MockitoBean
    private ReleaseStockMapper mapper;

    @Test
    void releasesStockAndReturnsOk() throws Exception {
        UUID variantId = UUID.randomUUID();
        ReleaseStockRequest request = new ReleaseStockRequest(4);
        ReleaseStockCommand command = new ReleaseStockCommand(variantId, 4);
        ReleaseStockResult result = new ReleaseStockResult(UUID.randomUUID(), variantId, 8, 2);
        ReleaseStockResponse response = new ReleaseStockResponse(
                result.inventoryId(), result.variantId(), result.availableQuantity(), result.reservedQuantity());

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(releaseStockUseCase.release(command)).willReturn(result);
        given(mapper.toResponse(result)).willReturn(response);

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/release", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableQuantity").value(8))
                .andExpect(jsonPath("$.reservedQuantity").value(2));
    }

    @Test
    void translatesInsufficientReservedStockExceptionIntoBadRequest() throws Exception {
        UUID variantId = UUID.randomUUID();
        ReleaseStockRequest request = new ReleaseStockRequest(100);
        ReleaseStockCommand command = new ReleaseStockCommand(variantId, 100);

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(releaseStockUseCase.release(command)).willThrow(new InsufficientReservedStockException(3, 100));

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/release", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("INSUFFICIENT_RESERVED_STOCK"));
    }

    @Test
    void translatesInventoryNotFoundExceptionIntoNotFound() throws Exception {
        UUID variantId = UUID.randomUUID();
        ReleaseStockRequest request = new ReleaseStockRequest(4);
        ReleaseStockCommand command = new ReleaseStockCommand(variantId, 4);

        given(mapper.toCommand(variantId, request)).willReturn(command);
        given(releaseStockUseCase.release(command)).willThrow(new InventoryNotFoundException());

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/release", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("INVENTORY_NOT_FOUND"));
    }

    @Test
    void rejectsANonPositiveQuantityWithValidationError() throws Exception {
        UUID variantId = UUID.randomUUID();
        ReleaseStockRequest request = new ReleaseStockRequest(0);

        mockMvc.perform(post("/api/v1/inventory/inventories/{variantId}/release", variantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
