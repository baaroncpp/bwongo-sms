CREATE TABLE t_merchant(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_by_id BIGINT REFERENCES t_user(id),
    is_deleted BOOLEAN DEFAULT false,
    country_id BIGINT REFERENCES t_country(id),
    email VARCHAR(150) UNIQUE,
    phone_number VARCHAR(15) UNIQUE,
    merchant_name VARCHAR(150) NOT NULL,
    merchant_code VARCHAR(15) UNIQUE NOT NULL,
    location TEXT,
    is_active BOOLEAN DEFAULT FALSE,
    merchant_type VARCHAR(50) NOT NULL,
    non_verified_phone_number BOOLEAN DEFAULT TRUE,
    activated_by_id BIGINT REFERENCES t_user(id),
    merchant_status VARCHAR(50) NOT NULL
);

CREATE TABLE t_merchant_activation(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    merchant_id BIGINT NOT NULL REFERENCES t_merchant(id),
    activation_code VARCHAR(10) NOT NULL,
    activation_count INTEGER,
    is_resend BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT FALSE,
    activation_code_status VARCHAR(50) NOT NULL
);

CREATE TABLE t_merchant_api_setting(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_by_id BIGINT REFERENCES t_user(id),
    is_deleted BOOLEAN DEFAULT false,
    merchant_id BIGINT NOT NULL REFERENCES t_merchant(id),
    api_key TEXT UNIQUE NOT NULL,
    api_secret TEXT UNIQUE NOT NULL,
    is_key_issued BOOLEAN DEFAULT FALSE,
    is_credential_expired BOOLEAN DEFAULT FALSE,
    key_expiry_date TIMESTAMP
);

CREATE TABLE t_merchant_sms_setting(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_by_id BIGINT REFERENCES t_user(id),
    is_deleted BOOLEAN DEFAULT false,
    customized_title VARCHAR(100),
    merchant_id BIGINT NOT NULL REFERENCES t_merchant(id),
    sms_cost NUMERIC NOT NULL,
    max_number_of_characters_per_sms INTEGER NOT NULL,
    is_customized BOOLEAN DEFAULT FALSE
);