WITH adminUser AS (SELECT id FROM t_user WHERE email = 'admin@bwongo.com')
INSERT INTO t_merchant(created_by_id, merchant_name, merchant_code, is_active, merchant_type, activated_by_id, merchant_status)
VALUES
    ((select id from adminUser), 'SYSTEM', '100001', true, 'SYSTEM', (select id from adminUser), 'ACTIVE');

WITH
    adminUser AS (SELECT id FROM t_user WHERE email = 'admin@bwongo.com'),
    merchant AS (SELECT id FROM t_merchant WHERE merchant_code = '100001')
INSERT INTO t_account(created_by_id, name, code, status, merchant_id, account_type, account_category, activated_by)
VALUES
    ((select id from adminUser), 'SYSTEM_CREDIT', '10011001', 'ACTIVE', (select id from merchant), 'CREDIT', 'SYSTEM', (select id from adminUser)),
    ((select id from adminUser), 'SYSTEM_DEBIT', '10010001', 'ACTIVE', (select id from merchant), 'DEBIT', 'SYSTEM', (select id from adminUser));