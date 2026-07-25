CREATE TABLE inventories
(
    id                 UUID PRIMARY KEY,
    variant_id         UUID        NOT NULL UNIQUE,
    available_quantity INT         NOT NULL DEFAULT 0,
    reserved_quantity  INT         NOT NULL DEFAULT 0,
    created_at         TIMESTAMPTZ NOT NULL,
    updated_at         TIMESTAMPTZ,
    CONSTRAINT chk_inventories_available_non_negative CHECK (available_quantity >= 0),
    CONSTRAINT chk_inventories_reserved_non_negative CHECK (reserved_quantity >= 0)
);
