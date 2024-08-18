CREATE TABLE t_country (
    id BIGSERIAL primary key,
    created_on timestamp not null default now(),
    modified_on timestamp,
    name VARCHAR(50) not null,
    iso_alpha_2 CHARACTER(2) unique not null,
    iso_alpha_3 CHARACTER(3) unique not null,
    iso_numeric INTEGER unique not null
);

CREATE TABLE t_user_group(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP,
    user_group_name VARCHAR(50) UNIQUE,
    group_note TEXT
);

CREATE TABLE t_user(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    first_name VARCHAR(250),
    second_name VARCHAR(250),
    email VARCHAR(100) UNIQUE,
    password TEXT,
    account_locked BOOLEAN NOT NULL DEFAULT TRUE,
    account_expired BOOLEAN NOT NULL DEFAULT TRUE,
    cred_expired BOOLEAN NOT NULL DEFAULT TRUE,
    user_group_id BIGINT NOT NULL REFERENCES t_user_group(id),
    approved BOOLEAN NOT NULL DEFAULT TRUE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    approved_by INTEGER REFERENCES t_user(id),
    initial_password_reset BOOLEAN NOT NULL DEFAULT FALSE,
    user_type VARCHAR(50) NOT NULL,
    non_verified_email BOOLEAN NOT NULL DEFAULT TRUE,
    image_path TEXT,
    merchant_id BIGINT
);

CREATE TABLE t_role(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP,
    name VARCHAR(20) UNIQUE,
    note TEXT
);

CREATE TABLE t_permission(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP,
    role_id BIGINT NOT NULL REFERENCES t_role(id),
    permission_name VARCHAR(50) UNIQUE,
    is_assignable BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE t_group_authority(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP,
    user_group_id BIGINT NOT NULL REFERENCES t_user_group(id),
    permission_id BIGINT NOT NULL REFERENCES t_permission(id),
    UNIQUE (user_group_id, permission_id)
);

CREATE TABLE t_user_approval(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP,
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_by_id BIGINT REFERENCES t_user(id),
    is_deleted BOOLEAN NOT NULL DEFAULT TRUE,
    user_id BIGINT NOT NULL REFERENCES t_user(id),
    status VARCHAR(30) NOT NULL
);

CREATE TABLE t_previous_password(
    id BIGSERIAL primary key,
    created_on timestamp not null default now(),
    modified_on timestamp,
    user_id BIGINT NOT NULL REFERENCES t_user(id),
    previous_password TEXT,
    password_change_count INTEGER
);

CREATE TABLE t_refresh_token(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    token TEXT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    user_id BIGINT REFERENCES t_user(id)
);

CREATE TABLE t_address(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id BIGINT REFERENCES t_user(id),
    is_deleted BOOLEAN DEFAULT false,
    street VARCHAR(300),
    address_description TEXT,
    town_or_village VARCHAR(300),
    country_id BIGINT REFERENCES t_country(id),
    latitude_coordinate DOUBLE PRECISION,
    longitude_coordinate DOUBLE PRECISION
);