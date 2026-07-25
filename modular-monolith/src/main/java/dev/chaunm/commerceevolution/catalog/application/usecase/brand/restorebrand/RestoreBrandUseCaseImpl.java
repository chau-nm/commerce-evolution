package dev.chaunm.commerceevolution.catalog.application.usecase.brand.restorebrand;

import dev.chaunm.commerceevolution.catalog.domain.exception.brand.BrandNotFoundException;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.Brand;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.repository.brand.BrandRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestoreBrandUseCaseImpl implements RestoreBrandUseCase {

    private final BrandRepository brandRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public RestoreBrandResult restore(RestoreBrandCommand command) {
        Brand brand = brandRepository.findById(new BrandId(command.id()))
                .orElseThrow(BrandNotFoundException::new);

        brand.restore();

        Brand savedBrand = brandRepository.save(brand);
        brand.domainEvents().forEach(domainEventPublisher::publish);

        return new RestoreBrandResult(savedBrand.getId().value());
    }
}
