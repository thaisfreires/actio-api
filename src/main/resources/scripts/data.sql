USE actio_db;
GO;

INSERT INTO stock (stock_name) VALUES
                                   ('EDP.LS'),
                                   ('NOS.LS'),
                                   ('GALP.LS');


INSERT INTO actio_user (full_name, nif, date_of_birth, email, user_password, role_code) VALUES
                                                                                            ('Alice Pereira', '123456789', '1990-05-10', 'alice@teste.com', '$2a$10$EwXVLrD4UsyBO8TGd37gUuSdvfaLEs7iUEdGRXxNvyUSCl/S0kY3S', 2),
                                                                                            ('Bruno Costa', '987654321', '1985-11-22', 'bruno@teste.com', 'a$10$CdECcS6Gk5QveJoA9LgZ6OH4qi5qF3RqhHxGs3xxLNhlQ8eF5S5I.', 2),
                                                                                            ('Carla Silva', '456789123', '1992-02-15', 'carla@teste.com', 'a$10$IgIQNd5ny4QQf63TjKkEWekQdDi3kcdqmgtwrpgKTjKMMcOQVc67S', 2);

INSERT INTO account (id_user, current_balance, status_code) VALUES
                                                                (1, 10000.00, 1),
                                                                (2, 5000.00, 1),
                                                                (3, 7500.00, 1);

INSERT INTO movement (id_account, amount, type_code) VALUES
                                                         (1, 1000.00, 1), -- Alice
                                                         (2, 500.00, 1),  -- Bruno
                                                         (3, 250.00, 1);  -- Carla

INSERT INTO movement (id_account, amount, type_code) VALUES
                                                         (1, 300.00, 2),
                                                         (2, 200.00, 2);

-- Alice compra 10 ações da EDP.LS a 5.00 cada
INSERT INTO stock_transaction (id_account, id_stock, negotiation_price, quantity, type_code)
VALUES (1, 1, 5.00, 10, 1);

-- Bruno compra 5 ações da NOS.LS a 8.00 cada
INSERT INTO stock_transaction (id_account, id_stock, negotiation_price, quantity, type_code)
VALUES (2, 2, 8.00, 5, 1);

-- Carla compra 3 da GALP.LS a 15.00 cada
INSERT INTO stock_transaction (id_account, id_stock, negotiation_price, quantity, type_code)
VALUES (3, 3, 15.00, 3, 1);

-- Alice vende 5 ações da EDP.LS a 6.50 cada
INSERT INTO stock_transaction (id_account, id_stock, negotiation_price, quantity, type_code)
VALUES (1, 1, 6.50, 5, 2);
