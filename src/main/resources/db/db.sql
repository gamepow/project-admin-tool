use defaultdb;

-- Users Table
CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL, -- Consider hashing passwords securely!
    Email VARCHAR(255) UNIQUE,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- User Profiles Table (Optional, for more detailed user info)
CREATE TABLE UserProfiles (
    ProfileID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT UNIQUE,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),    
    DefaultCurrency VARCHAR(3) DEFAULT 'USD', -- ISO 4217 currency code
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Budget Goals Table
CREATE TABLE BudgetGoals (
    GoalID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    Month DATE NOT NULL, -- Store the first day of the month
    GoalAmount DECIMAL(10, 2) NOT NULL,
    Currency VARCHAR(3) DEFAULT 'CRC', -- Currency of the goal
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    UNIQUE (UserID, Month) -- Ensure only one budget goal per month per user
);

-- Categories Table
CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT, -- Allows user-specific categories
    CategoryName VARCHAR(255) NOT NULL,
    CategoryType ENUM('Expense', 'Income') NOT NULL, -- Expense or Income
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Transactions Table (Expenses/Incomes)
CREATE TABLE Transactions (
    TransactionID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    CategoryID INT,
    TransactionDate DATE NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    Currency VARCHAR(3) DEFAULT 'CRC',
    Description VARCHAR(255),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

-- Example Data (Optional)
-- Insert a test User
INSERT INTO Users (Username, Password, Email) VALUES ('test', '$2a$12$.77Rq/4aVblETmPbk8cuCO7fKbtZpfUhVOQTKyxPpmZO1ZumhuHaO', 'test@example.com');

-- Insert a test Budget Goal
INSERT INTO BudgetGoals (UserID, Month, GoalAmount, Currency) VALUES (1, '2024-07-01', 2000.00, 'CRC');

-- Insert example categories
INSERT INTO Categories (UserID, CategoryName, CategoryType) VALUES (1, 'Groceries', 'Expense'),(1, 'Salary', 'Income'), (1, 'Rent', 'Expense'),(1, 'Utilities', 'Expense');

-- Insert example transactions
INSERT INTO Transactions (UserID, CategoryID, TransactionDate, Amount, Currency, Description) VALUES (1, 1, '2024-07-05', 100.00, 'CRC', 'Weekly groceries'), (1, 2, '2024-07-01', 3000.00, 'CRC', 'Monthly Salary'),(1, 3, '2024-07-01', 800.00, 'CRC', 'Monthly Rent'),(1,4, '2024-07-03', 200.00, 'CRC', 'Electricity bill');