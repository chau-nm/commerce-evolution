package dev.chaunm.commerceevolution.catalog.presentation.brand.restorebrand;

import dev.chaunm.commerceevolution.catalog.application.usecase.brand.restorebrand.RestoreBrandMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.restorebrand.RestoreBrandResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.restorebrand.RestoreBrandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/brands/{id}/restore")
@RequiredArgsConstructor
public class RestoreBrandController {

    private final RestoreBrandUseCase restoreBrandUseCase;
    private final RestoreBrandMapper mapper;

    @PostMapping
    public ResponseEntity<RestoreBrandResponse> restore(@PathVariable UUID id) {
        RestoreBrandResult result = restoreBrandUseCase.restore(mapper.toCommand(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
