package dev.chaunm.commerceevolution.shared.presentation.pagination;

import java.util.List;

public record PaginationResponse<T>(
        List<T> items,
        int totalPages,
        int totalItems,
        int page,
        int pageSize,
        int pageItems
) {

}
