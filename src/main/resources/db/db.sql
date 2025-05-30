use defaultdb;

-- Users Table
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(25) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- Consider hashing passwords securely!
    email VARCHAR(255) UNIQUE,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- User Profiles Table (Optional, for more detailed user info)
CREATE TABLE userProfile (
    profile_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),    
    default_currency VARCHAR(3) DEFAULT 'USD', -- ISO 4217 currency code
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Budget Goals Table
CREATE TABLE budget(
    budget_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    start_date DATE NOT NULL, -- Store the first day of the month
    budget_amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'CRC', -- Currency of the goal
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE (user_id, start_date) -- Ensure only one budget goal per month per user
);

-- Categories Table
CREATE TABLE category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT, -- Allows user-specific categories
    category_name VARCHAR(255) NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Transactions Table (Expenses/Incomes)
CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    category_id INT,
    transaction_type ENUM('expense', 'income') NOT NULL, -- Expense or Income
    transaction_date DATE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_description VARCHAR(255),
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);

-- Example Data (Optional)
-- Insert a test User
INSERT INTO users (username, password, email) VALUES ('test', '$2a$10$NDj6Tc9jhiEIZKZ/6izdve576JXqP.Gf161/9TysxZQ5l/nNubyOO', 'test@example.com');

INSERT INTO userProfile (user_id, first_name, last_name, default_currency) VALUES (1,'Test', 'lastname','CRC');

-- Insert a test Budget Goal
INSERT INTO budget (user_id, start_date, budget_amount, currency) VALUES (1, '2024-07-01', 2000.00, 'CRC');

-- Insert example categories
INSERT INTO category (user_id, category_name) VALUES (null, 'Groceries'),(null, 'Salary'), (null, 'Rent'),(null, 'Utilities');

-- Insert example transactions
INSERT INTO transactions (user_id, category_id, transaction_type, transaction_date, amount, transaction_description) 
VALUES (1, 1, 'expense', '2024-07-05', 100.00, 'Weekly groceries'), 
(1, 2,'income', '2024-07-01', 3000.00, 'Monthly Salary'),
(1, 3,'expense', '2024-07-01', 800.00, 'Monthly Rent'),
(1,4,'expense', '2024-07-03', 200.00, 'Electricity bill');

-- added scripts

-- Rename the existing budget table to user_monthly_budget
RENAME TABLE budget TO user_monthly_budget;

-- Remove the budget_amount column from the user_monthly_budget table
ALTER TABLE user_monthly_budget
DROP COLUMN budget_amount;

CREATE TABLE budget_category_allocation (
    allocation_id INT PRIMARY KEY AUTO_INCREMENT,
    user_monthly_budget_id INT NOT NULL,
    category_id INT NOT NULL,
    budgeted_amount DECIMAL(10, 2) NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_monthly_budget_id) REFERENCES user_monthly_budget(budget_id),
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    UNIQUE (user_monthly_budget_id, category_id) -- Ensures only one allocation per category per monthly budget
);

CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    account_name VARCHAR(255) NOT NULL,
    account_type ENUM('checking', 'savings', 'credit_card', 'cash', 'investment', 'loan') NOT NULL,
    current_balance DECIMAL(15, 2) DEFAULT 0.00,
    currency VARCHAR(3) DEFAULT 'CRC',
    is_active BOOLEAN DEFAULT TRUE,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE (user_id, account_name) -- Ensure unique account names for a user
);

-- Then, modify your transactions table to link to an account:
ALTER TABLE transactions
ADD COLUMN account_id INT AFTER user_id;

ALTER TABLE transactions
ADD CONSTRAINT fk_transactions_account
FOREIGN KEY (account_id) REFERENCES accounts(account_id);

-- Adding a category_type column (Optional)
ALTER TABLE category
ADD COLUMN category_type ENUM('expense', 'income', 'transfer') AFTER category_name;

-- Adding parent_category_id for subcategories (Optional, but highly recommended for richer budgeting)
ALTER TABLE category
ADD COLUMN parent_category_id INT AFTER category_name;

ALTER TABLE category
ADD CONSTRAINT fk_category_parent
FOREIGN KEY (parent_category_id) REFERENCES category(category_id);

INSERT INTO user_monthly_budget (user_id, start_date, currency) VALUES (1, '2024-07-01', 'CRC');

-- Assuming budget_id 1 is for user 1, 2024-07-01
INSERT INTO budget_category_allocation (user_monthly_budget_id, category_id, budgeted_amount) VALUES
(1, (SELECT category_id FROM category WHERE category_name = 'Groceries' AND user_id IS NULL), 400.00),
(1, (SELECT category_id FROM category WHERE category_name = 'Rent' AND user_id IS NULL), 800.00),
(1, (SELECT category_id FROM category WHERE category_name = 'Utilities' AND user_id IS NULL), 250.00);

update category set category_type = 'expense' where category_id in (1,2,4);
update category set category_type = 'income' where category_id in (3);