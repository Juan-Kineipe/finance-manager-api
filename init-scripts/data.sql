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


INSERT INTO users (user_name, first_name, last_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES
    ('juan', 'Juan', 'Test', '$2a$10$8791PPc0iYD36p.lcX.J1OZXXnAjhE28u1SSf3gJ/Od3xssgocgUO', true, true, true, true),
    ('gaby', 'Gaby', 'Test', '$2a$10$TZQDxz3E0wfost0LC7knQ.Ghog/mxjx3UjG2vob.S0mPbiBlNAIZW', true, true, true, true);

INSERT INTO permission (description) VALUES ('ADMIN'), ('USER');

INSERT INTO user_permission (id_user, id_permission) VALUES (1, 1), (1, 2), (2, 2);

INSERT INTO categories (name, type) VALUES ('Housing', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Groceries', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Transportation', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Taxes', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Subscriptions', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Restaurants', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Shopping', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Leisure', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Gifts', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Savings', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Others', 'EXPENSE');
INSERT INTO categories (name, type) VALUES ('Salary', 'INCOME');
INSERT INTO categories (name, type) VALUES ('Bonus', 'INCOME');
INSERT INTO categories (name, type) VALUES ('Savings', 'INCOME');
INSERT INTO categories (name, type) VALUES ('Others', 'INCOME');

INSERT INTO accounts (name, type, balance, user_id) VALUES ('Checking', 'CHECKING', 6750.7, 1);
INSERT INTO accounts (name, type, balance, user_id) VALUES ('Savings', 'SAVINGS', 4619.8, 1);

INSERT INTO credit_cards (name, credit_limit, user_id) VALUES ('Credit', 1133.0, 1);

INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (1200.0, '2024-11-01T09:00:00', 'Monthly Rent', 1, 1, 2);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (80.0, '2024-11-02T10:30:00', 'Electricity Bill', 1, 1, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (250.0, '2024-11-03T14:30:00', 'Supermarket Groceries', 1, 2, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (45.0, '2024-11-05T17:00:00', 'Fresh Produce Market', 1, 2, 2);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (50.0, '2024-11-04T08:15:00', 'Bus Pass', 1, 3, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (20.0, '2024-11-06T07:45:00', 'Gasoline', 1, 3, 2);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (300.0, '2024-11-05T11:00:00', 'Income Tax Payment', 1, 4, 2);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (15.0, '2024-11-06T10:00:00', 'Music Streaming Service', 1, 5, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (12.0, '2024-11-07T08:00:00', 'Cloud Storage Subscription', 1, 5, 2);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (40.0, '2024-11-07T19:30:00', 'Dinner at Italian Restaurant', 1, 6, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (25.0, '2024-11-09T13:00:00', 'Lunch with Friends', 1, 6, 2);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (150.0, '2024-11-08T15:00:00', 'Clothes Shopping', 1, 7, 2);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (75.0, '2024-11-10T20:00:00', 'Movie Night', 1, 8, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (30.0, '2024-11-12T14:00:00', 'Amusement Park Tickets', 1, 8, 2);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (100.0, '2024-11-12T12:00:00', 'Birthday Gift', 1, 9, 2);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (60.0, '2024-11-15T17:00:00', 'Holiday Gift for Colleague', 1, 9, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (200.0, '2024-11-13T09:00:00', 'Monthly Savings', 1, 10, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (50.0, '2024-11-14T16:00:00', 'Miscellaneous Expenses', 1, 11, 2);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (30.0, '2024-11-16T18:30:00', 'Emergency Repairs', 1, 11, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (4000.0, '2024-11-15T08:00:00', 'Monthly Salary', 1, 12, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (500.0, '2024-11-16T18:00:00', 'Quarterly Bonus', 1, 13, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (300.0, '2024-11-17T10:00:00', 'Savings Contribution', 1, 14, 2);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (50.0, '2024-11-18T15:00:00', 'Freelance Work', 1, 15, 1);
INSERT INTO transactions (amount, date, description, user_id, category_id, account_id) VALUES (150.0, '2024-11-19T12:00:00', 'Investment Returns', 1, 15, 2);


/* Expense */
INSERT INTO expectations (amount, user_id, category_id) VALUES (1200.0, 1, 1); -- Housing
INSERT INTO expectations (amount, user_id, category_id) VALUES (500.0, 1, 2); -- Groceries
INSERT INTO expectations (amount, user_id, category_id) VALUES (150.0, 1, 3); -- Transportation
INSERT INTO expectations (amount, user_id, category_id) VALUES (200.0, 1, 4); -- Taxes
INSERT INTO expectations (amount, user_id, category_id) VALUES (50.0, 1, 5); -- Subscriptions
INSERT INTO expectations (amount, user_id, category_id) VALUES (300.0, 1, 6); -- Restaurants
INSERT INTO expectations (amount, user_id, category_id) VALUES (250.0, 1, 7); -- Shopping
INSERT INTO expectations (amount, user_id, category_id) VALUES (100.0, 1, 8); -- Leisure
INSERT INTO expectations (amount, user_id, category_id) VALUES (80.0, 1, 9); -- Gifts
INSERT INTO expectations (amount, user_id, category_id) VALUES (100.0, 1, 10); -- Savings
INSERT INTO expectations (amount, user_id, category_id) VALUES (150.0, 1, 11); -- Others
/* Income */
INSERT INTO expectations (amount, user_id, category_id) VALUES (150.0, 1, 12); -- Salary
INSERT INTO expectations (amount, user_id, category_id) VALUES (20.0, 1, 13); -- Bonus
INSERT INTO expectations (amount, user_id, category_id) VALUES (0.0, 1, 15); -- Others