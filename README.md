# 🧑‍💼 Employee Management System

A backend Employee Management System built using **Java, Spring Boot, Spring Data JPA, Hibernate and MySQL**.

The project provides REST APIs for managing employees, departments, attendance, leaves, salaries and users.

It also includes dedicated reporting APIs demonstrating practical SQL concepts such as:

- INNER JOIN
- LEFT JOIN
- SELF JOIN
- UNION
- Subqueries
- Correlated subqueries
- GROUP BY
- COUNT
- AVG
- ORDER BY
- Aggregate functions

---

## 🚀 Tech Stack

| Technology | Usage |
|---|---|
| Java 17 | Backend programming language |
| Spring Boot | Application framework |
| Spring Web | REST APIs |
| Spring Data JPA | Database interaction |
| Hibernate | ORM |
| MySQL | Relational database |
| ModelMapper | DTO ↔ Entity mapping |
| Lombok | Boilerplate reduction |
| Maven | Dependency management |
| IntelliJ IDEA | Development environment |

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