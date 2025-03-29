# Employee Reimbursement System (ERS)

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [API Documentation](#api-documentation)
- [Setup Guide](#setup-guide)
- [Database Schema](#database-schema)
- [Deployment](#deployment)
- [Testing](#testing)
- [Security](#security)
- [Troubleshooting](#troubleshooting)
- [Roadmap](#roadmap)
- [License](#license)

## Features
**Employee Functions**
✔ Submit new reimbursement requests
✔ View all past reimbursement tickets
✔ Filter requests by "Pending" status

**Manager Functions**
✔ Approve or deny reimbursement requests
✔ View all employee submissions
✔ Delete user accounts (with cascading reimbursements)
✔ Promote user to manager role

**Core System**
🔒 Session-based authentication
📱 Responsive Tailwind CSS frontend
📊 PostgreSQL database with proper constraints

*(Optional features shown in Swagger but not required for core functionality)*

## Tech Stack
**Frontend**
- React 18 + TypeScript
- Tailwind CSS
- React Hot Toast + Hero Icons 

**Backend**
- Spring Boot 3.4 (Java 17)
- Spring Security
- Spring Session
- Spring Data JPA

**Database**
- PostgreSQL 15 (Supabase)

**Infrastructure**
- Frontend: Netlify
- Backend: Render
- Database: Supabase

## API Documentation
### Live Swagger UI
[![Swagger](https://img.shields.io/badge/Interactive_Docs-Swagger-85EA2D?style=for-the-badge&logo=swagger)](https://reimburse-mate.onrender.com/swagger-ui/index.html)

**Sample Requests**
```bash
# Login
curl -X POST "https://reimburse-mate.onrender.com/api/auth/login" \
-H "Content-Type: application/json" \
-d '{"username":"employee1","password":"pass123"}'

# Submit Reimbursement
curl -X POST "https://reimburse-mate.onrender.com/api/reimbursements" \
-H "Cookie: JSESSIONID=..." \
-d '{"amount":150.00,"description":"Office supplies"}'
```

## Setup Guide
### Local Development
1. **Clone Repository**
 ```bash
 git clone https://github.com/vlrnsnk/reimbursemate.git
 cd reimbursemate
 ```

2. **Backend Setup**
 ```bash
 cd backend
 # Configure src/main/resources/application.properties 
 mvn spring-boot:run
 ```

Here's the rewritten **Backend Setup** section for your README.md using `application.properties` (Maven-based Spring Boot):

---

## **Backend Setup** (Spring Boot)

### Prerequisites
- Java 17+
- Maven 3.9+
- PostgreSQL 15+

### Configuration
1. **Edit `src/main/resources/application.properties`**:
```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/ers
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=validate

# Session
server.servlet.session.timeout=30m
server.servlet.session.cookie.http-only=true
server.servlet.session.cookie.secure=true

# Security (for production)
spring.security.user.name=admin
spring.security.user.password=temp_password
```

2. **Run with Maven**:
```bash
mvn spring-boot:run
```

3. **Frontend Setup**
```bash
cd ../frontend
npm install
npm run dev
```

## Database Schema
```sql
-- Users Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'EMPLOYEE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Reimbursements Table
CREATE TABLE reimbursements (
    id SERIAL PRIMARY KEY,
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    comment TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    approver_id INT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Deployment
### Production Environment
1. **Backend (Render)**
 - Runtime: Java 17
 - Build Command: `mvn clean package`
 - Start Command: `java -jar target/ers-backend.jar`
 - Environment Variables:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://[supabase-host]:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
 ```

2. **Frontend (Netlify)**
 - Build Command: `npm run build`
 - Publish Directory: `dist`

## Testing
**Backend Tests**
```bash
mvn test
```

## Security
| Measure | Implementation |
|---------|----------------|
| Session Based | Authentication and Authorization |
| CSRF Protection | Spring Security default |
| CORS Policy | Restricted to frontend domain |
| SQL Injection | Prepared statements via JPA |

## Troubleshooting
| Issue | Solution |
|-------|----------|
| Session not persisting | Ensure `SameSite=None` and `Secure` flags for cookies in production |
| Hibernate LazyInitializationException | Add `@Transactional` to service methods |

## Roadmap
- [ ] Email notifications (SendGrid)
- [ ] Refactor to use JWT
- [ ] Advanced reporting (PDF export)

## License
This project is licensed under the [MIT License](LICENSE).
