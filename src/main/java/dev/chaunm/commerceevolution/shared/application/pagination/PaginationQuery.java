package dev.chaunm.commerceevolution.shared.application.pagination;

import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record PaginationQuery(
        int pageIndex,
        int pageSize,
        String sortBy,
        Sort.Direction order
) {

    public static PaginationQuery from(PaginationRequest request) {
        return new PaginationQuery(
                request.page() - 1,
                request.pageSize(),
                request.sortBy(),
                request.order()
        );
    }

    public Pageable toPageable() {
        return PageRequest.of(pageIndex, pageSize, Sort.by(order, sortBy));
    }
}
