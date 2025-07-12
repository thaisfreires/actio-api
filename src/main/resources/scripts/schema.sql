-- ========================
-- AUX TABLES
-- ========================
USE actio_db;
GO;


CREATE TABLE user_role (
                           role_code INT PRIMARY KEY IDENTITY(1,1),
                           role_description VARCHAR(15) NOT NULL
);

CREATE TABLE account_status (
                                status_code INT PRIMARY KEY IDENTITY(1,1),
                                status_description VARCHAR(15) NOT NULL
);

CREATE TABLE movement_type (
                               type_code INT PRIMARY KEY IDENTITY(1,1),
                               type_description VARCHAR(15) NOT NULL
);

CREATE TABLE transaction_type (
                                  type_code INT PRIMARY KEY IDENTITY(1,1),
                                  type_description VARCHAR(15) NOT NULL
);

CREATE TABLE stock (
                       id_stock INT PRIMARY KEY IDENTITY(1,1),
                       stock_name VARCHAR(10) NOT NULL UNIQUE
);

-- ========================
-- MAIN TABLES
-- ========================
CREATE TABLE actio_user (
                            id_user INT PRIMARY KEY IDENTITY(1,1),
                            full_name VARCHAR(250) NOT NULL,
                            nif CHAR(9) NOT NULL UNIQUE,
                            date_of_birth DATE NOT NULL,
                            email VARCHAR(100) NOT NULL UNIQUE,
                            user_password VARCHAR(100) NOT NULL,
                            role_code INT,
                            CONSTRAINT fk_actio_user_role FOREIGN KEY (role_code) REFERENCES user_role(role_code),
                            CONSTRAINT ck_actio_user_full_name CHECK (LEN(full_name) >= 2 AND LEN(full_name) <= 250),
                            CONSTRAINT ck_actio_user_nif CHECK (LEN(nif) = 9),
                            CONSTRAINT ck_actio_user_user_password CHECK (LEN(user_password) >= 9)
);

CREATE TABLE account (
                         id_account INT PRIMARY KEY IDENTITY(1,1),
                         id_user INT NOT NULL,
                         current_balance DECIMAL(18,2) DEFAULT 0.0,
                         status_code INT NOT NULL,
                         CONSTRAINT fk_account_user FOREIGN KEY (id_user) REFERENCES actio_user(id_user),
                         CONSTRAINT fk_account_status FOREIGN KEY (status_code) REFERENCES account_status(status_code)
);

CREATE TABLE stock_item (
                            id_account INT NOT NULL,
                            id_stock INT NOT NULL,
                            quantity INT NOT NULL CHECK (quantity >= 0),
                            CONSTRAINT pk_stock_item PRIMARY KEY (id_account, id_stock),
                            CONSTRAINT fk_stock_item_account FOREIGN KEY (id_account) REFERENCES account(id_account),
                            CONSTRAINT fk_stock_item_stock FOREIGN KEY (id_stock) REFERENCES stock(id_stock)
);

CREATE TABLE movement (
                          id_movement INT PRIMARY KEY IDENTITY(1,1),
                          id_account INT NOT NULL,
                          amount DECIMAL(18,2) NOT NULL CHECK (amount > 0),
                          type_code INT NOT NULL,
                          movement_date_time DATETIME NOT NULL DEFAULT GETDATE(),
                          CONSTRAINT fk_movement_account FOREIGN KEY (id_account) REFERENCES account(id_account),
                          CONSTRAINT fk_movement_type FOREIGN KEY (type_code) REFERENCES movement_type(type_code)
);

CREATE TABLE stock_transaction (
                                   id_transaction INT PRIMARY KEY IDENTITY(1,1),
                                   id_account INT NOT NULL,
                                   id_stock INT NOT NULL,
                                   negotiation_price DECIMAL(18,2) NOT NULL CHECK (negotiation_price > 0),
                                   quantity INT NOT NULL CHECK (quantity > 0),
                                   type_code INT NOT NULL,
                                   transaction_date_time DATETIME NOT NULL DEFAULT GETDATE(),
                                   CONSTRAINT fk_transaction_account FOREIGN KEY (id_account) REFERENCES account(id_account),
                                   CONSTRAINT fk_transaction_stock FOREIGN KEY (id_stock) REFERENCES stock(id_stock),
                                   CONSTRAINT fk_transaction_type FOREIGN KEY (type_code) REFERENCES transaction_type(type_code)
);
