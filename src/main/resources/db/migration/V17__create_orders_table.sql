CREATE TABLE orders
(
    id              UUID PRIMARY KEY,
    order_number    VARCHAR(50)  NOT NULL UNIQUE,
    customer_id     UUID         NOT NULL,
    status          VARCHAR(20)  NOT NULL,
    total_amount    BIGINT       NOT NULL,
    recipient_name  VARCHAR(255) NOT NULL,
    recipient_phone VARCHAR(20)  NOT NULL,
    province        VARCHAR(255) NOT NULL,
    district        VARCHAR(255) NOT NULL,
    ward            VARCHAR(255) NOT NULL,
    street          VARCHAR(255) NOT NULL,
    postal_code     VARCHAR(20),
    created_at      TIMESTAMPTZ  NOT NULL,
    updated_at      TIMESTAMPTZ
);

CREATE INDEX idx_orders_customer_id ON orders (customer_id);

CREATE TABLE order_items
(
    id           UUID PRIMARY KEY,
    order_id     UUID         NOT NULL REFERENCES orders (id),
    variant_id   UUID         NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    variant_name VARCHAR(255) NOT NULL,
    unit_price   BIGINT       NOT NULL,
    quantity     INT          NOT NULL,
    subtotal     BIGINT       NOT NULL,
    created_at   TIMESTAMPTZ  NOT NULL,
    updated_at   TIMESTAMPTZ,
    CONSTRAINT chk_order_item_quantity_positive CHECK (quantity > 0)
);

CREATE INDEX idx_order_items_order_id ON order_items (order_id);
