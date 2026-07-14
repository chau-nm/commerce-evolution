package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.repository;

import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.Slug;
import dev.chaunm.commerceevolution.catalog.domain.repository.ProductRepository;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
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
    public Product save(Product product) {
        return productMapper.toDomain(
                jpaProductRepository.save(productMapper.toEntity(product))
        );
    }
}
