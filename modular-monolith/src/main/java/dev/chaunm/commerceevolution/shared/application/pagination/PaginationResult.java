package dev.chaunm.commerceevolution.shared.application.pagination;

import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PaginationResult<T>(
        List<T> content,
        int page,
        int pageSize,
        long totalItems
) {

    public int totalPages() {
        return pageSize == 0 ? 0 : (int) Math.ceil((double) totalItems / pageSize);
    }

    public <R> PaginationResult<R> map(Function<T, R> mapper) {
        return new PaginationResult<>(
                content.stream().map(mapper).toList(),
                page,
                pageSize,
                totalItems
        );
    }

    public PaginationResponse<T> toResponse() {
        return new PaginationResponse<>(
                content,
                totalPages(),
                (int) totalItems,
                page + 1,
                pageSize,
                content.size()
        );
    }

    public static <T> PaginationResult<T> from(Page<T> page) {
        return new PaginationResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
    }
}
