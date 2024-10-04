CREATE TABLE t_invoice
(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    invoice_number VARCHAR(150),
    issued_on_date TIMESTAMP,
    total_amount NUMERIC(19, 4),
    merchant_id BIGINT REFERENCES t_merchant(id),
    invoice_status VARCHAR(150),
    payment_due_date TIMESTAMP,
    quantity INTEGER
);

CREATE TABLE t_invoice_item
(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    invoice_id BIGINT REFERENCES t_invoice(id),
    sms_id BIGINT REFERENCES t_sms(id)
);