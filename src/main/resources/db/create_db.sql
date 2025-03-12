CREATE DATABASE finance_manager;
USE finance_manager;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(255) DEFAULT NULL,
    first_name VARCHAR(255) DEFAULT NULL,
    last_name VARCHAR(255) DEFAULT NULL,
    password VARCHAR(255) DEFAULT NULL,
    account_non_expired BIT(1) DEFAULT NULL,
    account_non_locked BIT(1) DEFAULT NULL,
    credentials_non_expired BIT(1) DEFAULT NULL,
    enabled BIT(1) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_name (user_name)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS permission (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    description VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_permission (
    id_user BIGINT(20) NOT NULL,
    id_permission BIGINT(20) NOT NULL,
    PRIMARY KEY (id_user, id_permission),
    KEY fk_user_permission_permission (id_permission),
    CONSTRAINT fk_user_permission FOREIGN KEY (id_user) REFERENCES users (id),
    CONSTRAINT fk_user_permission_permission FOREIGN KEY (id_permission) REFERENCES permission (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    balance FLOAT(53),
    name VARCHAR(255),
    type ENUM ('CHECKING', 'SAVINGS'),
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_accounts_user_id FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS credit_cards (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    credit_limit FLOAT(53),
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_credit_cards_user_id FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    type ENUM ('EXPENSE', 'INCOME'),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    amount FLOAT(53),
    date DATETIME(6),
    description VARCHAR(255),
    account_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_transactions_account_id FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT fk_transactions_category_id FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_transactions_user_id FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS expectations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    amount FLOAT(53),
    category_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_expectations_category_id FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_expectations_user_id FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;