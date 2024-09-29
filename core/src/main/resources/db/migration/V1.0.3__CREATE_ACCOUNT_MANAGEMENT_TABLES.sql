CREATE TABLE t_account
(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_by_id BIGINT REFERENCES t_user (id),
    is_deleted BOOLEAN DEFAULT false,
    name VARCHAR(250) NOT NULL,
    code VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    merchant_id BIGINT REFERENCES t_merchant(id),
    current_balance NUMERIC(19, 4),
    account_type VARCHAR(150),
    account_category VARCHAR(150),
    balance_to_notify_at NUMERIC(19, 4),
    balance_notification_sent_on TIMESTAMP,
    activated_on TIMESTAMP,
    activated_by BIGINT REFERENCES t_user(id),
    suspended_on TIMESTAMP,
    suspended_by BIGINT REFERENCES t_user(id),
    closed_on TIMESTAMP,
    closed_by BIGINT REFERENCES t_user(id)
);

CREATE TABLE t_account_transaction
(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    account_id BIGINT REFERENCES t_account(id),
    transaction_type VARCHAR(50) NOT NULL,
    non_reversal BOOLEAN DEFAULT FALSE,
    status VARCHAR(50),
    status_description TEXT,
    balance_before NUMERIC(19, 4),
    balance_after NUMERIC(19, 4),
    external_transaction_id TEXT,
    internal_transaction_id TEXT
);

CREATE TABLE t_cash_flow
(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    external_reference TEXT,
    internal_reference TEXT UNIQUE,
    amount NUMERIC(19, 4),
    from_account_transaction_id BIGINT REFERENCES t_account_transaction(id),
    to_account_transaction_id BIGINT REFERENCES t_account_transaction(id),
    from_account_id BIGINT REFERENCES t_account(id),
    to_account_id BIGINT REFERENCES t_account(id),
    flow_type VARCHAR(50)
);

CREATE TABLE t_momo_deposit
(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    created_by_id BIGINT NOT NULL REFERENCES t_user (id),
    modified_by_id BIGINT REFERENCES t_user (id),
    is_deleted BOOLEAN DEFAULT false,
    amount_deposit NUMERIC(19, 4),
    transaction_status VARCHAR(50),
    msisdn VARCHAR(15) NOT NULL,
    external_reference_id TEXT,
    depositor_name VARCHAR(150),
    merchant_id BIGINT REFERENCES t_merchant(id),
    network_type VARCHAR(50)
);