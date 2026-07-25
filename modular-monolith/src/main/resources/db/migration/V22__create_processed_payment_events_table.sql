CREATE TABLE processed_payment_events
(
    payment_id UUID PRIMARY KEY,
    order_id   UUID        NOT NULL,
    status     VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);
