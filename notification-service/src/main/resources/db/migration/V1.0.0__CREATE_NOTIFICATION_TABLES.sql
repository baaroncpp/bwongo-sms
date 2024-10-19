CREATE TABLE t_notification
(
    id BIGSERIAL primary key,
    created_on timestamp not null default now(),
    modified_on timestamp,
    sender VARCHAR(250) NOT NULL,
    recipient VARCHAR(250) NOT NULL,
    message TEXT,
    status VARCHAR(150) NOT NULL,
    merchant_code VARCHAR(50),
    notification_type VARCHAR(50),
    external_reference TEXT,
    event_id TEXT
);