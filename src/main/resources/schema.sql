CREATE TABLE user_profile (
    id BIGINT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    nif VARCHAR(9) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50)
);
