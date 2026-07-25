package dev.chaunm.commerceevolution.catalog.presentation.brand.getbrand;

import dev.chaunm.commerceevolution.catalog.application.usecase.brand.getbrand.GetBrandMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.getbrand.GetBrandResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.getbrand.GetBrandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/brands/{id}")
@RequiredArgsConstructor
public class GetBrandController {

    private final GetBrandUseCase getBrandUseCase;
    private final GetBrandMapper mapper;

    @GetMapping
    public ResponseEntity<GetBrandResponse> getBrand(@PathVariable UUID id) {
        GetBrandResult result = getBrandUseCase.getBrand(mapper.toCommand(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
