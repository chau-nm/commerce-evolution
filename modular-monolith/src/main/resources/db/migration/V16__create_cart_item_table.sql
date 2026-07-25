CREATE TABLE cart_item
(
    id         UUID PRIMARY KEY,
    cart_id    UUID        NOT NULL REFERENCES cart (id),
    variant_id UUID        NOT NULL,
    quantity   INT         NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ,
    CONSTRAINT chk_cart_item_quantity_positive CHECK (quantity > 0)
);

CREATE INDEX idx_cart_item_cart_id ON cart_item (cart_id);

CREATE UNIQUE INDEX uq_cart_item_cart_variant ON cart_item (cart_id, variant_id);
