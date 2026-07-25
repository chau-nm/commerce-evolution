package dev.chaunm.commerceevolution.catalog.application.usecase.brand.listbrands;

import dev.chaunm.commerceevolution.catalog.domain.repository.brand.BrandRepository;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListBrandsUseCaseImpl implements ListBrandsUseCase {

    private final BrandRepository brandRepository;

    @Override
    @Transactional(readOnly = true)
    public PaginationResult<BrandSummaryItem> list(ListBrandsCommand command) {
        PaginationQuery query = PaginationQuery.from(command.pagination());

        return brandRepository.findAll(query)
                .map(brand -> new BrandSummaryItem(brand.getId().value(), brand.getName().value()));
    }
}
