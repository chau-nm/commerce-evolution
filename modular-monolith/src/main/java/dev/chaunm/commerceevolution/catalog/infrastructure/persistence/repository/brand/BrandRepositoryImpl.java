package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.repository.brand;

import dev.chaunm.commerceevolution.catalog.domain.model.brand.Brand;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.repository.brand.BrandRepository;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper.brand.BrandMapper;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepository {

    private final JpaBrandRepository jpaBrandRepository;
    private final BrandMapper brandMapper;

    @Override
    public Optional<Brand> findById(BrandId id) {
        return jpaBrandRepository.findById(id.value())
                .map(brandMapper::toDomain);
    }

    @Override
    public PaginationResult<Brand> findAll(PaginationQuery query) {
        return PaginationResult.from(jpaBrandRepository.findByDeletedAtIsNull(query.toPageable()))
                .map(brandMapper::toDomain);
    }

    @Override
    public Brand save(Brand brand) {
        return brandMapper.toDomain(
                jpaBrandRepository.save(brandMapper.toEntity(brand))
        );
    }
}
