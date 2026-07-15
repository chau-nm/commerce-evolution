package dev.chaunm.commerceevolution.catalog.presentation.getvariantdetail;

import dev.chaunm.commerceevolution.catalog.application.usecase.getvariantdetail.GetVariantDetailMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.getvariantdetail.GetVariantDetailResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.getvariantdetail.GetVariantDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/variants/{variantId}")
@RequiredArgsConstructor
public class GetVariantDetailController {

    private final GetVariantDetailUseCase getVariantDetailUseCase;
    private final GetVariantDetailMapper mapper;

    @GetMapping
    public ResponseEntity<GetVariantDetailResponse> getDetail(
            @PathVariable UUID productId,
            @PathVariable UUID variantId
    ) {
        GetVariantDetailResult result = getVariantDetailUseCase.getDetail(mapper.toCommand(productId, variantId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
