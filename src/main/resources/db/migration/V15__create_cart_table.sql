CREATE TABLE cart
(
    id          UUID PRIMARY KEY,
    customer_id UUID        NOT NULL UNIQUE,
    created_at  TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ
);
