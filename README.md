# 🧾 Inventory API — Spring Boot

**Inventory API** adalah mini project berbasis **Spring Boot** untuk manajemen produk dengan fitur autentikasi JWT, role-based access, caching, logging, dan global error handling.

Project ini dibuat untuk memenuhi assignment **REST API Development using Spring Boot, JPA, and MySQL**.

---

## 🚀 Tech Stack

* **Java 17**
* **Spring Boot 3**
* **Spring Security (JWT)**
* **Spring Data JPA (Hibernate)**
* **MySQL**
* **Lombok**
* **Spring Cache**
* **Postman**

---

## ✨ Fitur Utama

* 🔐 Authentication & Authorization menggunakan JWT
* 👥 Role Based Access (`ADMIN`, `STAFF`)
* 📦 CRUD & manajemen stok produk
* ⚡ Caching untuk performa endpoint
* ⚙️ Global error handling
* 🪵 Logging aktivitas API

---

## ⚙️ Cara Menjalankan Project

### 1️⃣ Clone Repository

```bash
git clone https://github.com/<username>/inventory-api.git
cd inventory-api
```

### 2️⃣ Konfigurasi Database

Edit file `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password

jwt.secret=your-super-secret-key-32-chars-minimum
jwt.expiration=3600000
```

### 3️⃣ Jalankan Aplikasi

```bash
mvn spring-boot:run
```

Aplikasi akan berjalan di:

```
http://localhost:8081
```

---

## 🧠 API Endpoints

### 🔹 Authentication

| Method | Endpoint         | Deskripsi               |
| ------ | ---------------- | ----------------------- |
| POST   | `/auth/register` | Register user           |
| POST   | `/auth/login`    | Login & mendapatkan JWT |

**Request Register**

```json
{
  "username": "admin",
  "password": "123456",
  "role": "ADMIN"
}
```

**Request Login**

```json
{
  "username": "admin",
  "password": "123456"
}
```

**Response**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### 🔹 Product Management

Semua endpoint berikut membutuhkan header:

```
Authorization: Bearer <your_token>
```

| Method | Endpoint                   | Role         |
| ------ | -------------------------- | ------------ |
| GET    | `/products`                | ADMIN, STAFF |
| POST   | `/products`                | ADMIN        |
| PUT    | `/products/{id}/sell?qty=` | ADMIN, STAFF |

**Create Product**

```json
{
  "name": "Laptop ASUS",
  "price": 12000000,
  "stock": 10
}
```

**Sell Product**

```http
PUT /products/1/sell?qty=2
```

**Response**

```json
{
  "message": "Stock updated"
}
```

---

## 🔐 Role & Akses

| Role        | Hak Akses                  |
| ----------- | -------------------------- |
| ADMIN       | Create, Read, Sell Product |
| STAFF       | Read & Sell Product        |
| Tanpa Token | Tidak dapat mengakses API  |

---

## ⚡ Caching

* Endpoint `GET /products` menggunakan:

```java
@Cacheable("products")
```

* Cache akan dibersihkan saat create product:

```java
@CacheEvict(value = "products", allEntries = true)
```

---

## 🪵 Logging

Menggunakan `@Slf4j`:

```java
log.info("Creating product {}", request.getName());
log.info("Fetching all products");
log.warn("Invalid JWT token: {}", e.getMessage());
```

---

## ⚠️ Error Handling

Response error terstandarisasi:

```json
{
  "error": "Product not found"
}
```

Ditangani oleh `GlobalExceptionHandler`:

```java
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("error", ex.getMessage()));
}
```

---

## 🧑‍💻 Author

**Nama**: Wahid Firgiyanto
**Project**: Inventory API
**Tools**: IntelliJ IDEA, MySQL, Postman

