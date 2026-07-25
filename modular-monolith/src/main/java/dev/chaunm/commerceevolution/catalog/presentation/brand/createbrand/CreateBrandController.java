package dev.chaunm.commerceevolution.catalog.presentation.brand.createbrand;

import dev.chaunm.commerceevolution.catalog.application.usecase.brand.createbrand.CreateBrandMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.createbrand.CreateBrandResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.createbrand.CreateBrandUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/brands")
@RequiredArgsConstructor
public class CreateBrandController {

    private final CreateBrandUseCase createBrandUseCase;
    private final CreateBrandMapper mapper;

    @PostMapping
    public ResponseEntity<CreateBrandResponse> create(
            @Valid @RequestBody CreateBrandRequest request
    ) {
        CreateBrandResult result = createBrandUseCase.create(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
