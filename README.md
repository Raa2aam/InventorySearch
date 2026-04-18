# 📦 Inventory Search API

A production-ready REST API for searching and filtering inventory items built with Spring Boot and MySQL. Supports dynamic multi-parameter filtering, pagination, sorting, and comprehensive error handling.

---

## 🏗️ Design Approach

### Architecture

This project follows a clean **Layered Architecture** where each layer has one responsibility and only talks to the layer directly below it:

```
Client Request
      ↓
┌─────────────────────┐
│     Controller      │  ← Receives HTTP request, returns HTTP response
└─────────────────────┘
      ↓
┌─────────────────────┐
│      Service        │  ← Business logic, orchestration, mapping
└─────────────────────┘
      ↓
┌─────────────────────┐
│   Specification     │  ← Builds dynamic WHERE clause from filters
└─────────────────────┘
      ↓
┌─────────────────────┐
│    Repository       │  ← Talks to database
└─────────────────────┘
      ↓
┌─────────────────────┐
│       MySQL         │  ← Stores inventory data
└─────────────────────┘
```

### Key Design Patterns Used

| Pattern | Where Used | Why |
|---|---|---|
| **Specification Pattern** | `InventorySpecification.java` | Dynamically builds AND filters without messy if-else SQL strings |
| **DTO Pattern** | `dto/` package | Decouples internal DB schema from API contract |
| **Builder Pattern** | All DTOs and Entity | Clean, readable object construction |
| **Repository Pattern** | `InventoryRepository.java` | Abstracts all database operations |
| **Global Exception Handling** | `GlobalExceptionHandler.java` | Consistent error shape across all endpoints |

---

## 🔍 How Search Works

All search parameters are **optional** and combined with **AND logic**.

```
GET /api/v1/inventory/search?category=Electronics&minPrice=1000&maxPrice=50000
```

Internally the Specification pattern dynamically builds:

```sql
WHERE LOWER(category) LIKE '%electronics%'
AND price >= 1000
AND price <= 50000
```

If no filters are sent → returns all inventory items with default pagination (page 0, size 10).

---

## 📋 Assumptions & Considerations

- All string filters use **case-insensitive partial match** (LIKE) for better user experience — searching "samsung" finds "Samsung Electronics"
- Price is stored in **INR** using `BigDecimal` to avoid floating point precision issues — `Double` would cause errors like `0.1 + 0.2 = 0.30000000000000004`
- **Soft delete** is implemented via `isActive` flag — records are never hard deleted from the database for audit purposes
- Date filters support **range queries** (from/to) for flexibility — single date filter would be too rigid
- `createdAt` timestamp is **immutable** once set — enforced at ORM level via `@Column(updatable = false)`
- `stock` and `price` values cannot be negative — enforced via Bean Validation
- `minPrice` cannot be greater than `maxPrice` — enforced in service layer
- Seed data is loaded automatically on startup via `data.sql` using `INSERT IGNORE` for idempotency
- Page size is capped at 100 to prevent performance issues from large result sets

---

## 🚀 How to Run

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker

---

### Option 1 — Run with Docker Compose (Recommended for judges)

```bash
# Clone the repo
git clone https://github.com/YOUR_USERNAME/InventorySearch.git
cd InventorySearch

# Start MySQL + App together
docker-compose up --build
```

App will be available at:
```
http://localhost:8080
```

---

### Option 2 — Run Locally (Dev mode)

**Step 1 — Start MySQL via Docker:**
```bash
docker run --name inventory-mysql \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=inventorydb \
  -p 3307:3306 \
  -d mysql:8.0
```

**Step 2 — Run the Spring Boot app:**
```bash
mvn spring-boot:run
```

App will be available at:
```
http://localhost:8080
```

---

## 📡 API Reference

### Base URL
```
http://localhost:8080/api/v1
```

---

### Search Inventory

```
GET /api/v1/inventory/search
```

#### Query Parameters

| Parameter | Type | Required | Default | Description |
|---|---|---|---|---|
| `name` | String | No | - | Partial case-insensitive match |
| `category` | String | No | - | Partial case-insensitive match |
| `subcategory` | String | No | - | Partial case-insensitive match |
| `model` | String | No | - | Partial case-insensitive match |
| `seller` | String | No | - | Partial case-insensitive match |
| `location` | String | No | - | Partial case-insensitive match |
| `brand` | String | No | - | Partial case-insensitive match |
| `color` | String | No | - | Partial case-insensitive match |
| `minPrice` | Decimal | No | - | Minimum price in INR |
| `maxPrice` | Decimal | No | - | Maximum price in INR |
| `minStock` | Integer | No | - | Minimum stock quantity |
| `maxStock` | Integer | No | - | Maximum stock quantity |
| `manufacturingDateFrom` | Date | No | - | Format: `YYYY-MM-DD` |
| `manufacturingDateTo` | Date | No | - | Format: `YYYY-MM-DD` |
| `expiryDateFrom` | Date | No | - | Format: `YYYY-MM-DD` |
| `expiryDateTo` | Date | No | - | Format: `YYYY-MM-DD` |
| `isActive` | Boolean | No | - | `true` or `false` |
| `page` | Integer | No | `0` | Page number (0-based) |
| `size` | Integer | No | `10` | Items per page (max 100) |
| `sortBy` | String | No | `id` | Field name to sort by |
| `sortDir` | String | No | `asc` | `asc` or `desc` |

---

#### Sample Requests

**Get all inventory (paginated):**
```
GET /api/v1/inventory/search
```

**Get all Electronics:**
```
GET /api/v1/inventory/search?category=Electronics
```

**Get items between ₹1000 and ₹50000:**
```
GET /api/v1/inventory/search?minPrice=1000&maxPrice=50000
```

**Get Electronics under ₹30000 sorted by price ascending:**
```
GET /api/v1/inventory/search?category=Electronics&maxPrice=30000&sortBy=price&sortDir=asc
```

**Get out of stock items:**
```
GET /api/v1/inventory/search?maxStock=0
```

**Get inactive items:**
```
GET /api/v1/inventory/search?isActive=false
```

**Get second page with 5 items per page:**
```
GET /api/v1/inventory/search?page=1&size=5
```

**Complex filter — Samsung Electronics in Mumbai under ₹80000:**
```
GET /api/v1/inventory/search?brand=Samsung&category=Electronics&location=Mumbai&maxPrice=80000&sortBy=price&sortDir=asc
```

---

#### Success Response

```json
{
  "success": true,
  "currentPage": 0,
  "totalPages": 2,
  "totalItems": 17,
  "pageSize": 10,
  "data": [
    {
      "id": 1,
      "name": "Samsung 55\" 4K Smart TV",
      "category": "Electronics",
      "subcategory": "Television",
      "model": "UA55AU7700",
      "seller": "Samsung India",
      "location": "Mumbai",
      "brand": "Samsung",
      "color": "Black",
      "warranty": "1 Year",
      "specification": "4K UHD, HDR, Smart TV with WiFi",
      "price": 49999.00,
      "stock": 50,
      "manufacturingDate": "2023-01-15",
      "expiryDate": "2028-01-15",
      "isActive": true,
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00"
    }
  ]
}
```

#### Error Response

```json
{
  "success": false,
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "minPrice cannot be greater than maxPrice",
  "timestamp": "2024-01-01T10:00:00"
}
```

#### HTTP Status Codes

| Code | Meaning |
|---|---|
| `200` | Success |
| `400` | Bad request — invalid parameters |
| `500` | Internal server error |

---

## 🗄️ Entity Model

```
Inventory
├── id                  (PK, Auto Increment)
├── name                (NOT NULL)
├── category
├── subcategory
├── model
├── seller
├── location
├── brand
├── color
├── warranty
├── specification       (TEXT — long descriptions)
├── price               (BigDecimal — precision 10, scale 2)
├── stock               (NOT NULL)
├── manufacturingDate
├── expiryDate
├── isActive            (soft delete flag)
├── createdAt           (immutable — set once on create)
└── updatedAt           (updates on every save)
```

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Programming Language |
| Spring Boot | 3.2.x | Application Framework |
| Spring Data JPA | - | ORM and Repository abstraction |
| Hibernate | - | JPA Implementation |
| MySQL | 8.0 | Relational Database |
| Lombok | - | Boilerplate reduction |
| SpringDoc OpenAPI | 2.3.0 | Auto API Documentation |
| Docker | - | Containerization |
| Maven | - | Build Tool |

---

## 📖 API Documentation

Swagger UI (interactive — test APIs directly in browser):
```
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON spec:
```
http://localhost:8080/v3/api-docs
```

---

## 📁 Project Structure

```
InventorySearch/
├── src/
│   ├── main/
│   │   ├── java/com/yourname/inventorysearch/
│   │   │   ├── controller/
│   │   │   │   └── InventoryController.java     ← REST endpoint
│   │   │   ├── service/
│   │   │   │   └── InventoryService.java         ← Business logic
│   │   │   ├── repository/
│   │   │   │   └── InventoryRepository.java      ← DB operations
│   │   │   ├── model/
│   │   │   │   └── Inventory.java                ← JPA Entity
│   │   │   ├── dto/
│   │   │   │   ├── InventorySearchRequest.java   ← Filter params
│   │   │   │   ├── InventoryResponse.java        ← Response shape
│   │   │   │   └── PagedResponse.java            ← Pagination wrapper
│   │   │   ├── specification/
│   │   │   │   └── InventorySpecification.java   ← Dynamic filters ⭐
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java   ← Catches all errors
│   │   │   │   └── ErrorResponse.java            ← Error shape
│   │   │   └── config/
│   │   │       └── SwaggerConfig.java            ← API docs config
│   │   └── resources/
│   │       ├── application.properties            ← App configuration
│   │       └── data.sql                          ← Seed data
├── docker-compose.yml                            ← Run everything together
├── Dockerfile                                    ← Build app container
├── pom.xml                                       ← Dependencies
└── README.md                                     ← You are here
```

---

## 🌱 Seed Data

The app comes pre-loaded with **17 sample inventory items** across 5 categories:

| Category | Items |
|---|---|
| Electronics | Samsung TV, iPhone 15, Sony Headphones, Dell Laptop, OnePlus Nord |
| Furniture | Study Table, Office Chair, 3 Seater Sofa |
| Clothing | Nike Shoes, Levi Jeans, Allen Solly Shirt |
| Kitchen | Prestige Cooker, Philips Air Fryer, Bosch Microwave |
| Sports | Yonex Racket, Cosco Cricket Bat |

Includes one **inactive + out of stock** item for testing edge case filters.