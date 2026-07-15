package dev.chaunm.commerceevolution.shared.presentation.pagination;

import org.springframework.data.domain.Sort;

public record PaginationRequest(
        int page,
        int pageSize,
        String sortBy,
        Sort.Direction order
) {
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final Sort.Direction DEFAULT_ORDER = Sort.Direction.DESC;

    public PaginationRequest {
        if (page <= 0) {
            page = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (sortBy == null || sortBy.trim().isEmpty()) {
            sortBy = "id";
        }
        if (order == null) {
            order = DEFAULT_ORDER;
        }
    }
}
