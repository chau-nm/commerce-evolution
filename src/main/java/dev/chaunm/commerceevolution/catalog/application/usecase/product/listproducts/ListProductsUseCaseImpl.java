package dev.chaunm.commerceevolution.catalog.application.usecase.product.listproducts;

import dev.chaunm.commerceevolution.catalog.domain.exception.product.InvalidProductStatusFilterException;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductStatus;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListProductsUseCaseImpl implements ListProductsUseCase {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public PaginationResult<ProductSummaryItem> list(ListProductsCommand command) {
        ProductStatus status = parseStatus(command.status());
        PaginationQuery query = PaginationQuery.from(command.pagination());

        return productRepository.findAll(status, query)
                .map(product -> new ProductSummaryItem(
                        product.getId().value(),
                        product.getName().value(),
                        product.getSlug().value(),
                        product.getCategoryId() == null ? null : product.getCategoryId().value(),
                        product.getBrandId() == null ? null : product.getBrandId().value(),
                        product.getStatus().name()
                ));
    }

    private ProductStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }
        try {
            return ProductStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidProductStatusFilterException(status);
        }
    }
}
