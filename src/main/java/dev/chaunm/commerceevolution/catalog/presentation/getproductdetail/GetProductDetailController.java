package dev.chaunm.commerceevolution.catalog.presentation.getproductdetail;

import dev.chaunm.commerceevolution.catalog.application.usecase.getproductdetail.GetProductDetailMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.getproductdetail.GetProductDetailResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.getproductdetail.GetProductDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{id}")
@RequiredArgsConstructor
public class GetProductDetailController {

    private final GetProductDetailUseCase getProductDetailUseCase;
    private final GetProductDetailMapper mapper;

    @GetMapping
    public ResponseEntity<GetProductDetailResponse> getDetail(@PathVariable UUID id) {
        GetProductDetailResult result = getProductDetailUseCase.getDetail(mapper.toCommand(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
