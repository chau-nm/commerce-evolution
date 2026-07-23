package dev.chaunm.commerceevolution.catalog.domain.model.product;

import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductArchivedException;
import dev.chaunm.commerceevolution.catalog.domain.exception.product.ProductDeletedException;
import dev.chaunm.commerceevolution.catalog.domain.factory.product.ProductFactory;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.Slug;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = ProductFactory.create(new ProductName("Shirt"), new Slug("shirt"), null, null);
        product.clearDomainEvents();
    }

    @Test
    void assignCategoryRejectsWhenProductIsArchived() {
        product.publish();
        product.archive();

        assertThatThrownBy(() -> product.assignCategory(CategoryId.generate()))
                .isInstanceOf(ProductArchivedException.class);
    }

    @Test
    void removeCategoryRejectsWhenProductIsArchived() {
        product.assignCategory(CategoryId.generate());
        product.publish();
        product.archive();

        assertThatThrownBy(product::removeCategory)
                .isInstanceOf(ProductArchivedException.class);
    }

    @Test
    void changeBrandRejectsWhenProductIsArchived() {
        product.publish();
        product.archive();

        assertThatThrownBy(() -> product.changeBrand(BrandId.generate()))
                .isInstanceOf(ProductArchivedException.class);
    }

    @Test
    void removeBrandRejectsWhenProductIsArchived() {
        product.changeBrand(BrandId.generate());
        product.publish();
        product.archive();

        assertThatThrownBy(product::removeBrand)
                .isInstanceOf(ProductArchivedException.class);
    }

    @Test
    void assignCategoryRejectsWhenProductIsDeleted() {
        product.delete();

        assertThatThrownBy(() -> product.assignCategory(CategoryId.generate()))
                .isInstanceOf(ProductDeletedException.class);
    }

    @Test
    void updateDetailsRejectsWhenProductIsArchived() {
        product.publish();
        product.archive();

        assertThatThrownBy(() -> product.updateDetails(new ProductName("New name"), new Slug("new-slug")))
                .isInstanceOf(ProductArchivedException.class);
    }

    @Test
    void assignCategorySucceedsOnADraftProduct() {
        CategoryId categoryId = CategoryId.generate();

        product.assignCategory(categoryId);

        assertThat(product.getCategoryId()).isEqualTo(categoryId);
    }
}
