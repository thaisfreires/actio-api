-- ========================
-- INSERTS
-- ========================

USE actio_db;
GO;


INSERT INTO user_role (role_description) VALUES
                                             ('ADMIN'),
                                             ('CLIENT');

INSERT INTO account_status (status_description) VALUES
                                                    ('ACTIVE'),
                                                    ('SUSPENDED'),
                                                    ('CLOSED');

INSERT INTO movement_type (type_description) VALUES
                                                 ('DEPOSIT'),
                                                 ('RESCUE');

INSERT INTO transaction_type (type_description) VALUES
                                                    ('BUY'),
                                                    ('SELL');
