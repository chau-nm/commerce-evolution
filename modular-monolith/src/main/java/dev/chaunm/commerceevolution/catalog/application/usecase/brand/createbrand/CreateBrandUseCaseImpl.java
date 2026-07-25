package dev.chaunm.commerceevolution.catalog.application.usecase.brand.createbrand;

import dev.chaunm.commerceevolution.catalog.domain.factory.brand.BrandFactory;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.Brand;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandName;
import dev.chaunm.commerceevolution.catalog.domain.repository.brand.BrandRepository;
import dev.chaunm.commerceevolution.shared.infrastructure.event.SpringDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateBrandUseCaseImpl implements CreateBrandUseCase {

    private final BrandRepository brandRepository;
    private final SpringDomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public CreateBrandResult create(CreateBrandCommand command) {
        Brand brand = BrandFactory.create(new BrandName(command.name()));

        Brand savedBrand = brandRepository.save(brand);
        brand.domainEvents().forEach(domainEventPublisher::publish);

        return new CreateBrandResult(savedBrand.getId().value(), savedBrand.getName().value());
    }
}
