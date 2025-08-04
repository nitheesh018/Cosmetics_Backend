
# 💄 Cosmetics E-Commerce Backend

This is the **Spring Boot backend** for the Cosmetics E-Commerce platform. It exposes secure REST APIs to manage users, products, categories, reviews, orders, authentication, and admin operations.

## 📦 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL
- JWT Authentication
- Maven

## 🔐 Authentication

- JWT-based authentication
- Role-based access control (`USER`, `ADMIN`)
- BCrypt password encryption

## 🛍️ Features

- User registration and login
- View products and categories
- Submit and manage product reviews
- Admin product/category/user management
- Order checkout API
- Image upload via file or URL


## 🛠️ Setup Instructions

```bash
# 1. Clone the repository
git clone https://github.com/nitheesh018/Cosmetics_Backend.git
cd Cosmetics_Backend

# 2. Copy example config to application.properties
cp src/main/resources/application-example.properties src/main/resources/application.properties

# 3. Update database credentials and start the app
mvn spring-boot:run




