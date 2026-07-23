CREATE TABLE categories
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    parent_id  UUID         REFERENCES categories (id),
    deleted_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ  NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE INDEX idx_categories_parent_id ON categories (parent_id);
