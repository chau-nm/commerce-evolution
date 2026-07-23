CREATE TABLE customer
(
    id           UUID PRIMARY KEY,
    account_id   UUID         NOT NULL UNIQUE,
    full_name    VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20)  NOT NULL,
    birthday     DATE,
    gender       VARCHAR(16)  NOT NULL,
    created_at   TIMESTAMPTZ  NOT NULL,
    updated_at   TIMESTAMPTZ
);
