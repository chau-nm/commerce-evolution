CREATE TABLE product_media
(
    id         UUID          PRIMARY KEY,
    product_id UUID          NOT NULL REFERENCES products (id),
    url        VARCHAR(1024) NOT NULL,
    sort_order INT           NOT NULL DEFAULT 0,
    is_primary BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ   NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE INDEX idx_product_media_product_id ON product_media (product_id);
