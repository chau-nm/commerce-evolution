CREATE TABLE brands
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    deleted_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ  NOT NULL,
    updated_at TIMESTAMPTZ
);
