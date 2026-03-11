# Smart Inventory Management System

A backend application built with **Java, Spring Boot, Spring Security, Hibernate ORM, and MySQL** for managing stock, suppliers, billing, and user access in a multi-role environment.

---

## Features

- **Role-Based Access Control** — Three user roles (Admin, Manager, Viewer) with Spring Security + JWT authentication
- **Inventory Management** — Full CRUD for products with real-time stock tracking and low-stock alert triggers
- **Supplier Management** — Supplier profiles, contact details, and automatic reorder queue updates
- **Billing Module** — Invoice generation linked to stock deduction and supplier tracking
- **Secure API Layer** — Stateless JWT session management with token expiry and refresh logic
- **Optimized Queries** — Hibernate N+1 fetch fixes, indexing strategies, and lazy loading for performance

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot, Spring MVC, Spring Security |
| ORM | Hibernate, JDBC |
| Authentication | JWT, OAuth 2.0 concepts |
| Database | PostgreSQL |
| Build Tool | Maven |
| Version Control | Git, GitHub |

---

## Project Structure

```
InventoryManagement/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/inventory/
│   │   │       ├── controller/       # REST API endpoints
│   │   │       ├── service/          # Business logic layer
│   │   │       ├── repository/       # Data access layer (Hibernate)
│   │   │       ├── model/            # Entity classes
│   │   │       ├── security/         # JWT filters, Spring Security config
│   │   │       └── dto/              # Request/Response transfer objects
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

---

## Getting Started

### Prerequisites

- Java 17+
- MySQL 8.0+
- Maven 3.8+

### Setup

**1. Clone the repository**
```bash
git clone https://github.com/karthiGit03/Inventory-Management.git
cd Inventory-Management
```

**2. Configure the database**

Create a MySQL database and update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

**3. Build and run**
```bash
mvn clean install
mvn spring-boot:run
```

The application starts at `http://localhost:8080`

---

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |

### Inventory
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/inventory` | All roles | Get all products |
| POST | `/api/inventory` | Admin, Manager | Add new product |
| PUT | `/api/inventory/{id}` | Admin, Manager | Update product |
| DELETE | `/api/inventory/{id}` | Admin only | Delete product |

### Suppliers
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/suppliers` | All roles | List all suppliers |
| POST | `/api/suppliers` | Admin | Add supplier |
| PUT | `/api/suppliers/{id}` | Admin, Manager | Update supplier |

### Billing
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/billing` | Admin, Manager | Create invoice |
| GET | `/api/billing/{id}` | All roles | Get invoice by ID |

> All endpoints except `/api/auth/**` require a valid JWT token in the `Authorization: Bearer <token>` header.

---

## Security Flow

```
Client → POST /api/auth/login
       ← JWT Token

Client → GET /api/inventory
         Authorization: Bearer <token>
       → JwtAuthFilter validates token
       → SecurityContext set with user roles
       ← Response based on role permissions
```

---

## Author

**Karthikeyan Sankar**
- GitHub: [@karthiGit03](https://github.com/karthiGit03)
- LinkedIn: [karthi-sankar](https://www.linkedin.com/in/karthi-sankar)
- Email: karthikeyan030602@gmail.com
