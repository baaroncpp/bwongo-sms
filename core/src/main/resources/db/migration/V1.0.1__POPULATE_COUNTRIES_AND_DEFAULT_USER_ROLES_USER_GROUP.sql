INSERT INTO t_user_group(user_group_name, group_note)
VALUES
    ('ADMIN_GROUP', 'system administrative users'),
    ('MERCHANT_GROUP', 'merchant users');

INSERT INTO t_role(name, note)
VALUES
    ('ADMIN_ROLE', 'administrative role'),
    ('MERCHANT_ROLE', 'merchant role');

WITH role AS (SELECT id FROM t_role WHERE name = 'ADMIN_ROLE')
INSERT INTO t_permission(role_id, permission_name)
VALUES
    ((select id from role), 'ADMIN_ROLE.WRITE'),
    ((select id from role), 'ADMIN_ROLE.READ'),
    ((select id from role), 'ADMIN_ROLE.UPDATE'),
    ((select id from role), 'ADMIN_ROLE.DELETE');

WITH
    userGroup AS (SELECT id FROM t_user_group WHERE user_group_name = 'ADMIN_GROUP'),
    read AS (SELECT id FROM t_permission WHERE permission_name = 'ADMIN_ROLE.READ'),
    write AS (SELECT id FROM t_permission WHERE permission_name = 'ADMIN_ROLE.WRITE'),
    update AS (SELECT id FROM t_permission WHERE permission_name = 'ADMIN_ROLE.UPDATE'),
    delete AS (SELECT id FROM t_permission WHERE permission_name = 'ADMIN_ROLE.DELETE')

INSERT INTO t_group_authority(user_group_id, permission_id)
VALUES
    ((select id from userGroup), (select id from read)),
    ((select id from userGroup), (select id from write)),
    ((select id from userGroup), (select id from update)),
    ((select id from userGroup), (select id from delete));


WITH role AS (SELECT id FROM t_role WHERE name = 'MERCHANT_ROLE')
INSERT INTO t_permission(role_id, permission_name)
VALUES
    ((select id from role), 'MERCHANT_ROLE.WRITE'),
    ((select id from role), 'MERCHANT_ROLE.READ'),
    ((select id from role), 'MERCHANT_ROLE.UPDATE'),
    ((select id from role), 'MERCHANT_ROLE.DELETE');

WITH
    userGroup AS (SELECT id FROM t_user_group WHERE user_group_name = 'MERCHANT_GROUP'),
    read AS (SELECT id FROM t_permission WHERE permission_name = 'MERCHANT_ROLE.READ'),
    write AS (SELECT id FROM t_permission WHERE permission_name = 'MERCHANT_ROLE.WRITE'),
    update AS (SELECT id FROM t_permission WHERE permission_name = 'MERCHANT_ROLE.UPDATE'),
    delete AS (SELECT id FROM t_permission WHERE permission_name = 'MERCHANT_ROLE.DELETE')

INSERT INTO t_group_authority(user_group_id, permission_id)
VALUES
    ((select id from userGroup), (select id from read)),
    ((select id from userGroup), (select id from write)),
    ((select id from userGroup), (select id from update)),
    ((select id from userGroup), (select id from delete));


WITH
    userGroup AS (SELECT id FROM t_user_group WHERE user_group_name = 'ADMIN_GROUP')
INSERT INTO t_user(email, password, user_group_id, user_type, account_locked, account_expired, cred_expired)
VALUES('admin@bwongo.com', '$2y$12$R2gix.Nr/E4j9pmKVQHA4u/x.oyWf/wEPUBcQwxYerQSwqQXMcWZO', (select id from userGroup), 'ADMIN', FALSE, FALSE, FALSE);

INSERT INTO t_country (name,iso_alpha_2,iso_alpha_3,iso_numeric) VALUES
    ('UGANDA','UG','UGA',256),
    ('KENYA','KE','KEN',254),
    ('TANZANIA','TZ','TZA',255),
    ('RWANDA','RW','RWA',250);