WITH adminUser AS (SELECT id FROM t_user WHERE email = 'admin@bwongo.com')
INSERT INTO t_merchant(created_by_id, merchant_name, merchant_code, is_active, merchant_type, activated_by_id, merchant_status)
VALUES
    ((select id from adminUser), 'SYSTEM', '100001', true, 'SYSTEM', (select id from adminUser), 'ACTIVE');

WITH
    adminUser AS (SELECT id FROM t_user WHERE email = 'admin@bwongo.com'),
    merchant AS (SELECT id FROM t_merchant WHERE merchant_code = '100001')
INSERT INTO t_account(created_by_id, name, code, status, merchant_id, account_type, account_category, activated_by, current_balance)
VALUES
    ((select id from adminUser), 'SYSTEM_CREDIT', '10011001', 'ACTIVE', (select id from merchant), 'CREDIT', 'SYSTEM', (select id from adminUser), 0),
    ((select id from adminUser), 'SYSTEM_DEBIT', '10010001', 'ACTIVE', (select id from merchant), 'DEBIT', 'SYSTEM', (select id from adminUser), 0);

CREATE TABLE t_out_box_event(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    aggregator_type VARCHAR(250),
    aggregator_id BIGINT,
    type VARCHAR(250),
    payload TEXT,
    status VARCHAR(150)
);

CREATE TABLE t_notification(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    sender VARCHAR(250),
    recipient TEXT,
    message TEXT,
    status VARCHAR(250),
    merchant_code VARCHAR(50),
    notification_type VARCHAR(150),
    internal_reference TEXT,
    external_reference TEXT,
    event_id TEXT
);