CREATE TABLE notifications
(
    id           UUID PRIMARY KEY,
    recipient_id UUID         NOT NULL,
    type         VARCHAR(30)  NOT NULL,
    title        VARCHAR(255) NOT NULL,
    content      TEXT         NOT NULL,
    status       VARCHAR(20)  NOT NULL,
    read_at      TIMESTAMPTZ,
    created_at   TIMESTAMPTZ  NOT NULL,
    updated_at   TIMESTAMPTZ
);

CREATE INDEX idx_notifications_recipient_id ON notifications (recipient_id);
