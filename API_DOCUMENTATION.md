# Employee Management System — API & SQL Documentation

This document contains the detailed API and SQL documentation for the Employee Management System.

The main `README.md` should remain focused on the project's purpose, architecture, setup, key features, and a concise API overview. This file contains the deeper endpoint-by-endpoint and SQL details.

---

## 1. API Base URL

The application uses the following context path:

```text
http://localhost:8080/api/v1
```

---

# 2. Employee APIs

## Get all employees

```http
GET /api/v1/employees
```

Returns a list of all employees.

---

## Get employee by ID

```http
GET /api/v1/employees/{id}
```

Returns details of a specific employee.

Example:

```http
GET /api/v1/employees/101
```

---

## Create employee

```http
POST /api/v1/employee
```

Creates a new employee using `AddEmployeeDto`.

Example request:

```json
{
  "firstName": "Dhruvin",
  "lastName": "Patel",
  "email": "dhruvin@example.com",
  "phone": "9876543210",
  "dateOfBirth": "2004-01-01",
  "joiningDate": "2026-08-01",
  "designation": "Software Engineer",
  "salary": 50000,
  "status": "ACTIVE",
  "departmentId": 1,
  "managerId": null
}
```

---

## Delete employee

```http
DELETE /api/v1/employee/{id}
```

Deletes an employee by ID.

---

## Update employee

```http
PATCH /api/v1/employee/{id}
```

Partially updates employee fields.

Example:

```json
{
  "designation": "Senior Software Engineer",
  "salary": 70000
}
```

---

# 3. Department APIs

## Create department

```http
POST /api/v1/department
```

Creates a new department.

## Get all departments

```http
GET /api/v1/departments
```

Returns all departments.

## Get department by ID

```http
GET /api/v1/department/{id}
```

Returns a specific department.

## Update department

```http
PATCH /api/v1/department/{id}
```

Updates department information.

## Delete department

```http
DELETE /api/v1/department/{id}
```

Deletes a department.

---

# 4. Attendance APIs

## Mark attendance

```http
POST /api/v1/attendance
```

Creates an attendance record for an employee.

Example:

```json
{
  "employeeId": 101,
  "date": "2026-08-09",
  "checkIn": "09:30",
  "checkOut": "18:15"
}
```

### Business logic

The API:

1. Checks whether the employee exists.
2. Checks whether the employee is active.
3. Checks whether attendance already exists for that date.
4. Calculates working hours.
5. Determines attendance status.

### Status calculation

```text
>= 8 hours  → PRESENT
4-8 hours   → HALF_DAY
< 4 hours   → ABSENT
```

---

## Get employee attendance

```http
GET /api/v1/employees/{id}/attendance
```

Returns attendance records for an employee.

---

## Get monthly attendance

```http
GET /api/v1/employees/{id}/attendance?month=8&year=2026
```

Returns attendance records filtered by month and year.

SQL concepts involved:

```text
JOIN
MONTH()
YEAR()
ORDER BY
```

---

# 5. Leave APIs

## Apply for leave

```http
POST /api/v1/leaves
```

Creates a new leave request with `PENDING` status.

Example:

```json
{
  "employeeId": 101,
  "startDate": "2026-08-15",
  "endDate": "2026-08-17",
  "leaveType": "CASUAL",
  "reason": "Personal work"
}
```

### Business logic

```text
Employee exists?
       ↓
Employee active?
       ↓
Start date <= end date?
       ↓
Existing leave during same dates?
       ↓
Leave balance sufficient?
       ↓
Create PENDING leave
```

---

## Approve leave

```http
PUT /api/v1/leaves/{id}/approve
```

Approves a pending leave request after validating the approver and leave balance.

---

## Reject leave

```http
PUT /api/v1/leaves/{id}/reject
```

Rejects a pending leave request.

---

# 6. Salary APIs

## Generate salary

```http
POST /api/v1/salaries/generate
```

Generates salary for an employee for a particular month and year.

Example:

```json
{
  "employeeId": 101,
  "month": 8,
  "year": 2026
}
```

### Salary calculation

```text
Net Salary =
Basic Salary
+ Allowance
- Deduction
```

The API first checks the employee, verifies that salary has not already been generated, calculates the salary, and stores the result.

---

## Salary history

```http
GET /api/v1/employees/{id}/salaries
```

Returns salary history for an employee, ordered from newest to oldest.

---

# 7. User APIs

The `User` entity stores:

- Username
- Password
- Role
- Employee ID

Supported roles:

```text
ADMIN
HR
MANAGER
EMPLOYEE
```

A user can be associated with an employee through `employeeId`.

Example:

```json
{
  "username": "john.manager",
  "password": "password",
  "role": "MANAGER",
  "employeeId": 101
}
```

This means the user account belongs to employee `101` and has the `MANAGER` role.

> If authentication/security is not implemented yet, describe these as user-management capabilities rather than claiming that the roles enforce authorization.

---

# 8. Reports APIs

The Reports module demonstrates practical SQL operations involving multiple tables.

---

## 8.1 Employees with departments

```http
GET /api/v1/reports/employees-with-departments
```

Returns employees together with their department names.

### SQL

```sql
SELECT
    e.id,
    CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
    e.designation,
    d.name AS department
FROM employee e
INNER JOIN department d
    ON e.department_id = d.id;
```

### SQL concept

**INNER JOIN**

```text
employee
    │
    │ INNER JOIN
    ▼
department
```

### Why?

To combine employee information with the department to which the employee belongs.

---

## 8.2 Department employee count

```http
GET /api/v1/reports/department-summary
```

Returns the number of employees belonging to each department.

### SQL

```sql
SELECT
    d.id,
    d.name,
    COUNT(e.id) AS employee_count
FROM department d
LEFT JOIN employee e
    ON d.id = e.department_id
GROUP BY d.id, d.name;
```

### SQL concepts

```text
LEFT JOIN
COUNT()
GROUP BY
```

### Why LEFT JOIN?

A department should still appear even if it currently has zero employees.

---

## 8.3 Highest paid employees

```http
GET /api/v1/reports/highest-paid
```

Returns the top 10 highest-paid employees together with their departments.

### SQL

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
ORDER BY e.salary DESC
LIMIT 10;
```

### SQL concepts

```text
JOIN
ORDER BY
LIMIT
```

---

## 8.4 Employees earning above department average

```http
GET /api/v1/reports/employees-above-department-average
```

Returns employees whose salary is greater than the average salary of their own department.

### SQL

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

### SQL concepts

```text
JOIN
Subquery
AVG()
Correlated Subquery
```

### Why?

To compare each employee's salary against the average salary of their own department.

---

## 8.5 Employee and manager

```http
GET /api/v1/reports/employee-managers
```

Returns each employee together with their manager.

### SQL

```sql
SELECT
    e.first_name AS employee,
    m.first_name AS manager
FROM employee e
LEFT JOIN employee m
    ON e.manager_id = m.id;
```

### SQL concept

**SELF JOIN**

```text
employee e
    │
    │ SELF JOIN
    ▼
employee m
```

The same table is treated as two logical entities:

```text
e = Employee
m = Manager
```

### Why?

Because both employees and managers are stored in the same `employee` table.

---

## 8.6 Employees and managers using UNION

```http
GET /api/v1/reports/people
```

Combines active employees and managers into a common result format.

### SQL

```sql
SELECT
    id,
    first_name,
    last_name,
    'EMPLOYEE' AS type
FROM employee
WHERE status = 'ACTIVE'

UNION

SELECT
    e.id,
    e.first_name,
    e.last_name,
    'MANAGER' AS type
FROM employee e
WHERE e.id IN (
    SELECT DISTINCT manager_id
    FROM employee
    WHERE manager_id IS NOT NULL
);
```

### SQL concepts

```text
UNION
Subquery
DISTINCT
```

### Why UNION?

It combines two logically different result sets into one common result structure.

---

## 8.7 Employee activity — Attendance + Leave

```http
GET /api/v1/reports/employee-activity/{employeeId}
```

Combines attendance and leave activity into a single timeline.

Example:

```text
2026-08-01 | ATTENDANCE | PRESENT
2026-08-02 | ATTENDANCE | PRESENT
2026-08-03 | LEAVE      | APPROVED
2026-08-04 | ATTENDANCE | PRESENT
```

### SQL

```sql
SELECT
    employee_id,
    date AS activity_date,
    'ATTENDANCE' AS activity_type,
    status AS activity_status
FROM attendance
WHERE employee_id = ?

UNION

SELECT
    employee_id,
    start_date AS activity_date,
    'LEAVE' AS activity_type,
    status AS activity_status
FROM employee_leave
WHERE employee_id = ?

ORDER BY activity_date;
```

### Why UNION?

Attendance and leave are stored in separate tables, but both represent employee activity. `UNION` allows them to be presented as one combined timeline.

---

# 9. Dashboard API

```http
GET /api/v1/dashboard
```

Returns high-level statistics about the Employee Management System.

Example response:

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

The dashboard combines multiple aggregate database queries.

Example queries:

```sql
SELECT COUNT(*)
FROM employee;
```

```sql
SELECT COUNT(*)
FROM employee
WHERE status = 'ACTIVE';
```

```sql
SELECT COUNT(*)
FROM attendance
WHERE date = CURRENT_DATE
AND status = 'PRESENT';
```

```sql
SELECT SUM(net_salary)
FROM salary
WHERE month = ?
AND year = ?;
```

---

# 10. Database Relationships

The main database tables are:

```text
                    ┌──────────────┐
                    │  Department  │
                    └──────┬───────┘
                           │
                           │ department_id
                           ▼
                    ┌──────────────┐
                    │   Employee   │
                    └──────┬───────┘
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ▼                ▼                ▼
   ┌────────────┐   ┌────────────┐   ┌────────────┐
   │ Attendance │   │   Leave    │   │   Salary   │
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

---

# 11. SQL Concepts Demonstrated

| SQL Concept | Used For |
|---|---|
| INNER JOIN | Employee + Department |
| LEFT JOIN | Department + Employee count |
| SELF JOIN | Employee + Manager |
| UNION | Employee + Manager result sets |
| UNION | Attendance + Leave activity |
| GROUP BY | Department employee count |
| COUNT | Employee/department statistics |
| AVG | Department salary average |
| SUM | Payroll calculation |
| ORDER BY | Salary and attendance sorting |
| LIMIT | Top 10 highest-paid employees |
| Subquery | Department average salary |
| Correlated Subquery | Employee vs department average |
| MONTH | Monthly attendance filtering |
| YEAR | Monthly attendance filtering |
| DISTINCT | Finding unique managers |

---

# 12. Example End-to-End Workflow

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

# 13. Architecture

The project follows a layered Spring Boot architecture:

```text
Client
  │
  ▼
Controller
  │
  ▼
Service
  │
  ▼
Repository
  │
  ▼
MySQL Database
```

DTOs are used at the API boundary so that database entities are not directly exposed as request/response models.

The main layers are:

```text
controller
dto
entity
repository
service
service.impl
config
```

### Controller

Handles HTTP requests and exposes REST endpoints.

### Service

Contains application and business logic.

### Repository

Uses Spring Data JPA for database access.

### Entity

Represents database tables and relationships.

### DTO

Represents API request and response data.

### Mapper

ModelMapper is used for Entity ↔ DTO conversion.

---

# 14. Important Implementation Notes

- Verify every endpoint in this document against the actual controller before publishing.
- Keep endpoint naming consistent across controllers, README, and this document.
- Do not commit database passwords, API keys, or other secrets.
- Use environment variables or a local configuration file for sensitive values.
- Do not document authentication/authorization as implemented unless the corresponding Spring Security logic actually exists.
- Keep example data clearly identifiable as sample data.
- SQL shown here represents the reporting concepts implemented/planned in the project; update the queries if the actual repository queries differ.

---

# 15. Running the Project

## Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/employee-management-system.git
```

## Create the database

```sql
CREATE DATABASE ems;
```

## Configure MySQL

Example local configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ems
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.servlet.context-path=/api/v1
```

Never commit real credentials to GitHub.

## Run the application

Run:

```text
EmployeeManagementSystemApplication
```

The server starts at:

```text
http://localhost:8080
```

The API base path is:

```text
http://localhost:8080/api/v1
```

---

## Author

**Dhruvin Patel**

Spring Boot backend project demonstrating REST API development, layered architecture, relational database design, JPA/Hibernate, business logic, and practical SQL querying.
