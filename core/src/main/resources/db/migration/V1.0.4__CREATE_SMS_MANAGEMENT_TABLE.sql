CREATE TABLE t_sms
(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    created_by_id BIGINT NOT NULL REFERENCES t_user (id),
    modified_by_id BIGINT REFERENCES t_user (id),
    is_deleted BOOLEAN DEFAULT false,
    phone_number VARCHAR(15),
    message TEXT,
    sender VARCHAR(150),
    sms_status VARCHAR(150),
    is_resend BOOLEAN DEFAULT FALSE,
    resend_count INTEGER,
    merchant_id BIGINT REFERENCES t_merchant(id),
    internal_reference TEXT,
    external_reference TEXT,
    payment_status VARCHAR(150),
    cost NUMERIC(19, 4)
);