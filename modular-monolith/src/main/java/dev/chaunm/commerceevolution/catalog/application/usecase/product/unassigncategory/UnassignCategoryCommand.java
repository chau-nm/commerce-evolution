package dev.chaunm.commerceevolution.catalog.application.usecase.product.unassigncategory;

import java.util.UUID;

public record UnassignCategoryCommand(UUID productId) {
}
