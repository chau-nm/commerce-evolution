package dev.chaunm.commerceevolution.catalog.application.usecase.getproductdetail;

import dev.chaunm.commerceevolution.catalog.domain.exception.ProductNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetProductDetailUseCaseImpl implements GetProductDetailUseCase {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public GetProductDetailResult getDetail(GetProductDetailCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(ProductNotFoundException::new);

        return new GetProductDetailResult(
                product.getId().value(),
                product.getName().value(),
                product.getSlug().value(),
                product.getCategoryId().value(),
                product.getBrandId().value(),
                product.getStatus().name(),
                product.getVariants().stream()
                        .map(variant -> new GetProductDetailResult.VariantDetail(
                                variant.getId().value(),
                                variant.getSku().value(),
                                variant.getName(),
                                variant.isActive()
                        ))
                        .toList(),
                product.getMedias().stream()
                        .map(media -> new GetProductDetailResult.MediaDetail(
                                media.getId().value(),
                                media.getUrl(),
                                media.getSortOrder(),
                                media.isPrimary()
                        ))
                        .toList()
        );
    }
}
