# 🧑‍💼 Employee Management System

A backend Employee Management System built with **Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL**.

The project demonstrates practical backend development through REST APIs, layered architecture, relational database design, business logic, DTO mapping, and SQL reporting queries.

---

## ✨ Features

- 👨‍💼 Employee management
- 🏢 Department management
- 🕐 Employee attendance tracking
- 🏖️ Leave management
- 💰 Salary generation and history
- 👤 User and role management
- 📊 Dashboard statistics
- 📈 SQL-based reporting
- 🔗 Employee-manager relationships
- 🗄️ MySQL relational database
- 🔄 DTO ↔ Entity mapping with ModelMapper
- 🧩 Layered Spring Boot architecture

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| **Java 17** | Backend programming |
| **Spring Boot** | Application framework |
| **Spring Web** | REST API development |
| **Spring Data JPA** | Database access |
| **Hibernate** | ORM |
| **MySQL** | Relational database |
| **ModelMapper** | DTO ↔ Entity mapping |
| **Lombok** | Boilerplate reduction |
| **Maven** | Dependency management |
| **IntelliJ IDEA** | Development |

---

# 🏗️ Architecture

The project follows a layered architecture:

```text
                    Client
                      │
                      ▼
                ┌─────────────┐
                │ Controller  │
                └──────┬──────┘
                       │
                       ▼
                ┌─────────────┐
                │   Service   │
                └──────┬──────┘
                       │
                       ▼
                ┌─────────────┐
                │ Repository  │
                └──────┬──────┘
                       │
                       ▼
                ┌─────────────┐
                │    MySQL    │
                └─────────────┘
```

### Main layers

```text
controller
dto
entity
repository
service
service.impl
config
```

**Controller**  
Handles HTTP requests and exposes REST endpoints.

**Service**  
Contains application and business logic.

**Repository**  
Uses Spring Data JPA to communicate with the database.

**Entity**  
Represents database tables and relationships.

**DTO**  
Separates API request/response models from database entities.

**Mapper**  
ModelMapper is used for Entity ↔ DTO conversion.

---

# 📁 Project Structure

```text
src/main/java/com/example/employeeManagementSystem
│
├── config
│   └── mapperConfig
│
├── controller
│   ├── EmployeeController
│   ├── DepartmentController
│   ├── AttendanceController
│   ├── LeaveController
│   ├── SalaryController
│   ├── ReportController
│   └── DashboardController
│
├── dto
│
├── entity
│   ├── Employee
│   ├── Department
│   ├── Attendance
│   ├── Leave
│   ├── Salary
│   └── User
│
├── repository
│
├── service
│
└── service/impl
```

---

# 🗄️ Database Design

The main entities are:

```text
                    ┌──────────────┐
                    │  Department  │
                    └──────┬───────┘
                           │
                    department_id
                           │
                           ▼
                    ┌──────────────┐
                    │   Employee   │
                    └──────┬───────┘
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ▼                ▼                ▼
   ┌────────────┐   ┌────────────┐   ┌────────────┐
   │ Attendance │   │    Leave   │   │   Salary   │
   └────────────┘   └────────────┘   └────────────┘

                    ┌──────────────┐
                    │     User     │
                    └──────┬───────┘
                           │
                      employee_id
                           │
                           ▼
                    ┌──────────────┐
                    │   Employee   │
                    └──────────────┘
```

An employee can belong to a department and can have related attendance, leave, salary, and user records.

Employees can also have managers through a self-referencing employee relationship.

---

# 🔌 API Overview

The application uses:

```text
http://localhost:8080/api/v1
```

### Employee

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/employees` | Get all employees |
| GET | `/employees/{id}` | Get employee by ID |
| POST | `/employee` | Create employee |
| PATCH | `/employee/{id}` | Update employee |
| DELETE | `/employee/{id}` | Delete employee |

### Department

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/departments` | Get all departments |
| GET | `/department/{id}` | Get department |
| POST | `/department` | Create department |
| PATCH | `/department/{id}` | Update department |
| DELETE | `/department/{id}` | Delete department |

### Attendance

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/attendance` | Mark attendance |
| GET | `/employees/{id}/attendance` | Get employee attendance |
| GET | `/employees/{id}/attendance?month=8&year=2026` | Get monthly attendance |

### Leave

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/leaves` | Apply for leave |
| PUT | `/leaves/{id}/approve` | Approve leave |
| PUT | `/leaves/{id}/reject` | Reject leave |

### Salary

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/salaries/generate` | Generate salary |
| GET | `/employees/{id}/salaries` | Get salary history |

### Reports

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/reports/employees-with-departments` | Employee + department report |
| GET | `/reports/department-summary` | Department employee count |
| GET | `/reports/highest-paid` | Highest-paid employees |
| GET | `/reports/employees-above-department-average` | Salary comparison |
| GET | `/reports/employee-managers` | Employee-manager report |
| GET | `/reports/people` | Employee/manager UNION report |
| GET | `/reports/employee-activity/{employeeId}` | Attendance + leave timeline |

### Dashboard

```http
GET /api/v1/dashboard
```

Returns high-level system statistics such as:

```json
{
  "totalEmployees": 150,
  "activeEmployees": 142,
  "inactiveEmployees": 8,
  "totalDepartments": 6,
  "presentToday": 128,
  "absentToday": 10,
  "onLeaveToday": 4,
  "pendingLeaves": 7,
  "monthlyPayroll": 8500000
}
```


---

# 🧠 Business Logic

## Attendance

When attendance is marked, the application can validate:

1. Employee exists
2. Employee is active
3. Attendance does not already exist for that date
4. Working hours are calculated
5. Attendance status is determined

Example status rules:

```text
>= 8 hours  → PRESENT
4-8 hours   → HALF_DAY
< 4 hours   → ABSENT
```

---

## Leave

The leave workflow follows:

```text
Employee exists?
       ↓
Employee active?
       ↓
Valid date range?
       ↓
Existing overlapping leave?
       ↓
Leave balance sufficient?
       ↓
Create PENDING leave
       ↓
Approve / Reject
```

---

## Salary

Salary generation validates the employee and checks whether salary has already been generated for the selected month.

Conceptually:

```text
Net Salary =
Basic Salary
+ Allowance
- Deduction
```

---

# 📊 SQL & Reporting

The project demonstrates practical SQL concepts including:

| SQL Concept | Example |
|---|---|
| `INNER JOIN` | Employee + Department |
| `LEFT JOIN` | Department + Employee count |
| `SELF JOIN` | Employee + Manager |
| `UNION` | Employee + Manager results |
| `UNION` | Attendance + Leave timeline |
| `GROUP BY` | Department summaries |
| `COUNT()` | Employee statistics |
| `AVG()` | Department salary average |
| `SUM()` | Payroll totals |
| `ORDER BY` | Salary/attendance sorting |
| `LIMIT` | Top 10 employees |
| Subqueries | Salary comparisons |
| Correlated subqueries | Employee vs department average |
| `MONTH()` / `YEAR()` | Monthly attendance |

### Example: Employees above department average

```sql
SELECT
    e.id,
    e.first_name,
    e.last_name,
    e.salary,
    d.name AS department
FROM employee e
JOIN department d
    ON e.department_id = d.id
WHERE e.salary >
(
    SELECT AVG(e2.salary)
    FROM employee e2
    WHERE e2.department_id = e.department_id
);
```

This demonstrates a **correlated subquery** by comparing each employee's salary with the average salary of their own department.

For complete API details and SQL examples:

👉 **[Read the complete API & SQL Documentation](./API_DOCUMENTATION.md)**

---

# ⚙️ Configuration

Create a MySQL database:

```sql
CREATE DATABASE ems;
```

Configure your local database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ems
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.servlet.context-path=/api/v1
```

### 🔐 Security

**Never commit real database passwords, API keys, or other secrets to GitHub.**

Use environment variables or a local configuration file for sensitive values.

---

# ▶️ Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/employee-management-system.git
```

### 2. Open the project

Open the project in IntelliJ IDEA.

### 3. Create the MySQL database

```sql
CREATE DATABASE ems;
```

### 4. Configure database credentials

Update your local configuration with your MySQL username and password.

### 5. Run the application

Run:

```text
EmployeeManagementSystemApplication
```

The application will be available at:

```text
http://localhost:8080/api/v1
```

---

# 🧪 Example Workflow

A typical employee lifecycle can be represented as:

```text
Create Department
       ↓
Create Employee
       ↓
Create User
       ↓
Assign Role
       ↓
Mark Attendance
       ↓
Apply Leave
       ↓
Approve / Reject Leave
       ↓
Generate Salary
       ↓
View Reports
       ↓
View Dashboard
```

---

# 📚 Documentation

For detailed documentation including:

- Complete API endpoints
- Request examples
- Business logic
- SQL queries
- JOIN explanations
- UNION examples
- Subqueries
- Database relationships
- Dashboard queries

see:

**[API_DOCUMENTATION.md](./API_DOCUMENTATION.md)**

---

# 🚧 Future Improvements

Possible future enhancements include:

- Spring Security authentication
- JWT-based authorization
- Role-based access control
- Global exception handling
- Input validation
- Pagination and sorting
- API documentation with Swagger/OpenAPI
- Unit and integration testing
- Docker support
- Database migrations with Flyway
- CI/CD pipeline
- Frontend dashboard

---

# 👨‍💻 Author

**Dhruvin Patel**

Built as a practical Spring Boot backend project to demonstrate:

- REST API development
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- DTO architecture
- Business logic
- Relational database design
- SQL querying
- Layered backend architecture

---

## ⭐ Project Purpose

This project was built as a practical backend application rather than a simple CRUD demo.

The goal is to demonstrate how a real-world employee management system can combine **REST APIs, business rules, relational database relationships, and SQL reporting** in a structured Spring Boot application.
