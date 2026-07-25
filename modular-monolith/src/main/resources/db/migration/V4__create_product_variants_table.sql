CREATE TABLE product_variants
(
    id         UUID PRIMARY KEY,
    product_id UUID         NOT NULL REFERENCES products (id),
    sku        VARCHAR(64)  NOT NULL UNIQUE,
    name       VARCHAR(255) NOT NULL,
    active     BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ  NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE INDEX idx_product_variants_product_id ON product_variants (product_id);
