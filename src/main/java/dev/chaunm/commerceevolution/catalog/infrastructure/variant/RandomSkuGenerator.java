package dev.chaunm.commerceevolution.catalog.infrastructure.variant;

import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.service.variant.SkuGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Locale;

@Component
public class RandomSkuGenerator implements SkuGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String SUFFIX_CHARS = "0123456789";
    private static final int SUFFIX_LENGTH = 4;
    private static final int MAX_SKU_LENGTH = 64;
    private static final int MAX_BASE_LENGTH = MAX_SKU_LENGTH - SUFFIX_LENGTH - 1;

    @Override
    public SKU generate(ProductName productName, String variantName) {
        String seed = variantName == null || variantName.isBlank()
                ? productName.value()
                : productName.value() + "-" + variantName;

        String base = normalize(seed);
        if (base.length() > MAX_BASE_LENGTH) {
            base = base.substring(0, MAX_BASE_LENGTH);
        }

        return new SKU(base + "-" + randomSuffix());
    }

    private String normalize(String input) {
        String normalized = input.toUpperCase(Locale.ROOT)
                .replaceAll("[^A-Z0-9]+", "-")
                .replaceAll("^-+|-+$", "");
        return normalized.isBlank() ? "SKU" : normalized;
    }

    private String randomSuffix() {
        StringBuilder suffix = new StringBuilder(SUFFIX_LENGTH);
        for (int i = 0; i < SUFFIX_LENGTH; i++) {
            suffix.append(SUFFIX_CHARS.charAt(RANDOM.nextInt(SUFFIX_CHARS.length())));
        }
        return suffix.toString();
    }
}
