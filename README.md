
# 💰 DigiPocket – Personal Finance Management

A **production-ready backend** built with **Spring Boot 3**, **JWT Security**, and **MySQL**.  
Manage your personal finances easily, securely, and efficiently.  

---

## 🚀 Features

- 🔐 **User Authentication** – Registration & login with **BCrypt password hashing**  
- 🛡️ **JWT-Secured Endpoints** – Protect your APIs with token-based authentication  
- 💸 **Expense Management** – Create, list, update, and delete expenses  
- 📊 **Filtering & Sorting** – Filter by category/date range & sort by date  
- 🏗️ **Clean Architecture** – Controller → Service → Repository  
- ✅ **Validation & Exception Handling** – Global exception handling and input validation  
- 🐳 **Docker Support** – Run MySQL + Spring Boot app in containers  

---

## 🏁 Getting Started (Local Development)

### 1️⃣ Setup MySQL Database

```sql
CREATE DATABASE digipocket;
````

### 2️⃣ Configure Application

Update `src/main/resources/application.properties` with your MySQL credentials and set a **base64 JWT secret**:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/digipocket
spring.datasource.username=root
spring.datasource.password=changeme
app.jwt.secret=changeme
app.jwt.expiration-ms=86400000
```

### 3️⃣ Run the Application

```bash
mvn spring-boot:run
```

Your backend will be accessible at: `http://localhost:8080`

---

## 🐳 Running with Docker

1️⃣ Build the JAR:

```bash
mvn clean package -DskipTests
```

2️⃣ Start containers:

```bash
docker-compose up --build
```

* MySQL → `localhost:3307`
* Spring Boot App → `localhost:8080`
* App uses **environment variables** from `docker-compose.yml` to connect to MySQL

---

## 🧩 API Endpoints

### 🔑 Authentication

* **Register User**
  `POST /auth/register`

```json
{
  "username": "Akash",
  "email": "akash@example.com",
  "password": "secret123"
}
```

* **Login User**
  `POST /auth/login` → returns:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```

> Use `Authorization: Bearer <token>` for all protected endpoints.

---

### 💵 Expenses

* **Get Expenses**
  `GET /expenses?category=Food&from=2025-08-01&to=2025-08-30&sort=desc`

* **Create Expense**
  `POST /expenses`

```json
{
  "title": "Dinner",
  "category": "Food",
  "amount": 500,
  "date": "2025-08-16"
}
```

* **Update Expense**
  `PUT /expenses/{id}`

* **Delete Expense**
  `DELETE /expenses/{id}`

---

## 📂 Postman

* Import `DigiPocket.postman_collection.json` to quickly test all APIs.

---

## ⚡ Tech Stack

| Layer            | Technology             |
| ---------------- | ---------------------- |
| Backend          | Java 17, Spring Boot 3 |
| Security         | Spring Security, JWT   |
| Database         | MySQL 8.0              |
| Build            | Maven                  |
| Containerization | Docker, Docker Compose |
| Testing          | JUnit		            |

---

## 📌 Notes

* Use `application.properties` placeholders for GitHub, never commit secrets
* For Docker, you can create `application-docker.properties` ignored by GitHub for production credentials
* Make sure MySQL container is running before starting the Spring Boot container

---

✨ Happy budgeting with **DigiPocket!** ✨

---
