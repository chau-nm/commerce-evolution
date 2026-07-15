ALTER TABLE product_variants
    ADD COLUMN price BIGINT NOT NULL DEFAULT 0;

ALTER TABLE product_variants
    ALTER COLUMN price DROP DEFAULT;
