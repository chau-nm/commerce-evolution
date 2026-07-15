package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.ProductSummary;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductStatus;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.Slug;
import dev.chaunm.commerceevolution.catalog.domain.repository.ProductRepository;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.ProductEntity;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper.ProductMapper;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationQuery;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;
    private final JpaProductVariantRepository jpaProductVariantRepository;
    private final ProductMapper productMapper;

    @Override
    public boolean existsBySlug(Slug slug) {
        return jpaProductRepository.existsBySlug(slug.value());
    }

    @Override
    public boolean existsBySlugAndIdNot(Slug slug, ProductId id) {
        return jpaProductRepository.existsBySlugAndIdNot(slug.value(), id.value());
    }

    @Override
    public boolean existsByVariantSku(SKU sku) {
        return jpaProductVariantRepository.existsBySku(sku.value());
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return jpaProductRepository.findById(id.value())
                .map(productMapper::toDomain);
    }

    @Override
    public PaginationResult<ProductSummary> findAll(ProductStatus status, PaginationQuery query) {
        Pageable pageable = query.toPageable();

        Page<ProductEntity> page = status == null
                ? jpaProductRepository.findByDeletedAtIsNull(pageable)
                : jpaProductRepository.findByStatusAndDeletedAtIsNull(status, pageable);

        return PaginationResult.from(page)
                .map(entity -> new ProductSummary(
                        new ProductId(entity.getId()),
                        new ProductName(entity.getName()),
                        new Slug(entity.getSlug()),
                        new CategoryId(entity.getCategoryId()),
                        new BrandId(entity.getBrandId()),
                        entity.getStatus()
                ));
    }

    @Override
    public Product save(Product product) {
        return productMapper.toDomain(
                jpaProductRepository.save(productMapper.toEntity(product))
        );
    }
}
