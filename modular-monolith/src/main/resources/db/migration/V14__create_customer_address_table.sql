CREATE TABLE customer_address
(
    id             UUID PRIMARY KEY,
    customer_id    UUID         NOT NULL REFERENCES customer (id),
    recipient_name VARCHAR(255) NOT NULL,
    phone_number   VARCHAR(20)  NOT NULL,
    province       VARCHAR(255) NOT NULL,
    district       VARCHAR(255) NOT NULL,
    ward           VARCHAR(255) NOT NULL,
    street         VARCHAR(255) NOT NULL,
    postal_code    VARCHAR(20),
    is_default     BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at     TIMESTAMPTZ  NOT NULL,
    updated_at     TIMESTAMPTZ
);

CREATE INDEX idx_customer_address_customer_id ON customer_address (customer_id);

CREATE UNIQUE INDEX uq_customer_address_default_per_customer
    ON customer_address (customer_id)
    WHERE is_default = TRUE;
