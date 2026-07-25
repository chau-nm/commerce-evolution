CREATE TABLE payments
(
    id         UUID PRIMARY KEY,
    order_id   UUID        NOT NULL UNIQUE,
    amount     BIGINT      NOT NULL,
    status     VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ,
    CONSTRAINT chk_payments_amount_positive CHECK (amount > 0)
);
