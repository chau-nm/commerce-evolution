package dev.chaunm.commerceevolution.catalog.application.usecase.brand.getbrand;

import dev.chaunm.commerceevolution.catalog.domain.exception.brand.BrandNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.Brand;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.repository.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetBrandUseCaseImpl implements GetBrandUseCase {

    private final BrandRepository brandRepository;

    @Override
    @Transactional(readOnly = true)
    public GetBrandResult getBrand(GetBrandCommand command) {
        Brand brand = brandRepository.findById(new BrandId(command.id()))
                .orElseThrow(BrandNotFoundException::new);

        return new GetBrandResult(brand.getId().value(), brand.getName().value(), brand.getDeletedAt());
    }
}
