package dev.chaunm.commerceevolution.catalog.presentation.variant.listvariants;

import dev.chaunm.commerceevolution.catalog.application.usecase.variant.listvariants.ListVariantsMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.listvariants.ListVariantsResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.listvariants.ListVariantsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/variants")
@RequiredArgsConstructor
public class ListVariantsController {

    private final ListVariantsUseCase listVariantsUseCase;
    private final ListVariantsMapper mapper;

    @GetMapping
    public ResponseEntity<ListVariantsResponse> list(@PathVariable UUID productId) {
        ListVariantsResult result = listVariantsUseCase.list(mapper.toCommand(productId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
