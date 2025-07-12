
-- ========================
-- TRIGGER
-- ========================
USE actio_db;
GO;


-- FOR MOVEMENTS OPERATIONS
CREATE OR ALTER TRIGGER trg_update_account_balance
ON movement
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- Check balance
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN account a ON a.id_account = i.id_account
        WHERE i.type_code = 2 AND a.current_balance < i.amount
    )
BEGIN
        THROW 50002, 'Invalid operation: insufficient balance.', 1;
END;

    -- Deposit: add amount to balance
UPDATE account
SET current_balance = current_balance + i.amount
    FROM account a
    JOIN inserted i ON a.id_account = i.id_account
WHERE i.type_code = 1;

-- Rescue: subtract amount to balance
UPDATE account
SET current_balance = current_balance - i.amount
    FROM account a
    JOIN inserted i ON a.id_account = i.id_account
WHERE i.type_code = 2;
END;
GO;


-- FOR TRANSACTIONS OPERATIONS
CREATE OR ALTER TRIGGER trg_update_stock_item
ON stock_transaction
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- Validation: Block sell with insufficient stock
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN stock_item si
          ON si.id_account = i.id_account AND si.id_stock = i.id_stock
        WHERE i.type_code = 2 AND si.quantity < i.quantity
    )
BEGIN
        THROW 50001, 'Invalid operation: insufficient stock quantity.', 1;
END;

    -- Validation: Block buy with insufficient balance
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN account a ON a.id_account = i.id_account
        WHERE i.type_code = 1 AND a.current_balance < (i.negotiation_price * i.quantity)
    )
BEGIN
        THROW 50003, 'Invalid operation: insufficient account balance for BUY.', 1;
END;

    -- BUY: credit quantity or create element on stock_item
UPDATE si
SET si.quantity = si.quantity + i.quantity
    FROM stock_item si
    JOIN inserted i ON si.id_account = i.id_account AND si.id_stock = i.id_stock
WHERE i.type_code = 1;

INSERT INTO stock_item (id_account, id_stock, quantity)
SELECT i.id_account, i.id_stock, i.quantity
FROM inserted i
WHERE i.type_code = 1
  AND NOT EXISTS (
    SELECT 1 FROM stock_item si
    WHERE si.id_account = i.id_account AND si.id_stock = i.id_stock
);

-- SELL: subtract quantity on stock_item
UPDATE si
SET si.quantity = si.quantity - i.quantity
    FROM stock_item si
    JOIN inserted i ON si.id_account = i.id_account AND si.id_stock = i.id_stock
WHERE i.type_code = 2;

-- BUY: subtract value on Account balance
UPDATE a
SET a.current_balance = a.current_balance - (i.negotiation_price * i.quantity)
    FROM account a
    JOIN inserted i ON a.id_account = i.id_account
WHERE i.type_code = 1;

-- SELL: credit valor on Account balance
UPDATE a
SET a.current_balance = a.current_balance + (i.negotiation_price * i.quantity)
    FROM account a
    JOIN inserted i ON a.id_account = i.id_account
WHERE i.type_code = 2;
END;
GO;