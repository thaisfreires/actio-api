# Actio API – Investment Platform Backend

Actio is a full-stack investment platform that allows users to register, manage accounts, deposit and withdraw funds, trade stocks in real-time (buy/sell), and view their portfolio.

This is the backend service of the platform, built with **Spring Boot** and **Java 17**, and secured with JWT-based authentication. It connects to a **SQL Server** database and integrates with the **Alpha Vantage API** to fetch real-time stock quotes.

---

## 📌 Features

- 🧾 **User registration & login**
- 🔐 **JWT-based authentication & role-based access control**
- 💰 **Deposits and withdrawals (movement tracking)**
- 📈 **Buy and sell stocks using live EUR quotes**
- 🧾 **Transaction history for users and admins**
- 💼 **Wallet overview with current stock quantities and live prices**
- ⚙️ **Admin features for account status updates**
- 🌍 **RESTful API with CORS enabled**

---

## 🚀 Tech Stack

| Layer         | Tech                                    |
|---------------|-----------------------------------------|
| Language      | Java 17                                 |
| Framework     | Spring Boot 3                           |
| Auth          | Spring Security + JWT                   |
| DB            | SQL Server                              |
| API Client    | WebClient (for Alpha Vantage API)       |
| ORM           | Hibernate / JPA                         |
| Tools         | Lombok, Jakarta Validation, MapStruct   |

---

## 🔐 Authentication & Roles

Authentication is handled via JWT tokens.

- `CLIENT`: Regular users
- `ADMIN`: Admin users with extended privileges

Secure endpoints are annotated with `@PreAuthorize` to enforce access control.

---

## ⚙️ Getting Started

### ✅ Prerequisites

- Java 17+
- Maven
- SQL Server running locally or remotely
- Alpha Vantage API Key

---

### 🧑‍💻 Setup Instructions
1. **Clone the repository**
```bash
git clone https://github.com/your-user/actio-backend.git
cd actio-backend
```

2. **Create the database**

Create an empty SQL Server database (e.g. `actio_db`) and ensure that the application user has full access.


3. **Configure `application.properties`**

Create the file:
```bash
src/main/resources/application.properties
```

Example content:

```properties
spring.application.name=actio-api
```

# SQL Server JDBC URL
```bash
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=actio_db;encrypt=false
spring.datasource.username=actio_app_user
spring.datasource.password=YOURDBPASSWORD
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServer2012Dialect

# Server
server.port=8080

# Security
jwtKey=yourSecretJwtKey
apiKey=yourAlphaVantageApiKey
```

4. **Run the app**

Use Maven to start the application:

```bash
./mvnw spring-boot:run
```

On first run, if the database is empty (no actio_user table exists), the application will:

Execute SQL scripts to create schema and triggers

Populate enums and base data

Automatically load demo users, movements, and transactions

## 🔐 Authentication

| Method | Endpoint         | Description                                  |
|--------|------------------|----------------------------------------------|
| POST   | `/auth/login`    | Authenticates a user and returns a JWT token |

---

## 👤 Users

| Method | Endpoint              | Access        | Description                 |
|--------|-----------------------|---------------|-----------------------------|
| POST   | `/users/save`         | Public        | Register a new user         |
| GET    | `/users/user-info`    | CLIENT/ADMIN  | Get authenticated user info |

---

## 🧾 Accounts

| Method | Endpoint           | Access | Description                      |
|--------|--------------------|--------|----------------------------------|
| GET    | `/accounts/info`   | CLIENT | Get account details              |
| PUT    | `/accounts/status` | ADMIN  | Update user account status       |
| DELETE | `/accounts/delete` | CLIENT | Soft delete current user account |

---

## 💰 Movements

| Method | Endpoint             | Access        | Description          |
|--------|----------------------|---------------|----------------------|
| POST   | `/movements/deposit` | CLIENT        | Deposit funds        |
| POST   | `/movements/rescue`  | CLIENT        | Withdraw funds       |
| GET    | `/movements/history` | CLIENT/ADMIN  | Get movement history |

---

## 📈 Stock Transactions

| Method | Endpoint                | Access        | Description               |
|--------|-------------------------|---------------|---------------------------|
| POST   | `/transactions/buy`     | CLIENT        | Buy a stock               |
| POST   | `/transactions/sell`    | CLIENT        | Sell a stock              |
| GET    | `/transactions/getAll`  | CLIENT/ADMIN  | Get recent transactions   |

---

## 💼 Wallet

| Method | Endpoint                        | Access | Description                     |
|--------|----------------------------------|--------|---------------------------------|
| GET    | `/wallet`                        | CLIENT | Get user's current holdings     |
| GET    | `/wallet/{stockId}/quantity`     | CLIENT | Get quantity of specific stock |

---

## 📦 External API

| Method | Endpoint           | Access        | Description                          |
|--------|--------------------|---------------|--------------------------------------|
| GET    | `/stocks/{symbol}` | CLIENT/ADMIN  | Fetch stock quote via Alpha Vantage  |

---

## ✅ Notes

- All data is persisted in a SQL Server database.
- On first launch, if the tables do not exist, the app auto-creates schema and loads demo data.
- This app uses JWT-based authentication.
- External stock data is fetched via the Alpha Vantage API.

## 👥 Authors

- [Thais Freire](https://github.com/thaisfreires)  
- [Isabel Mendes](https://github.com/IsabelMendes1994)  
- [Pedro Tavares](https://github.com/PedroGuimaraesTavares)  
- [Priscila Campos](https://github.com/PriscilaGitHub)  
