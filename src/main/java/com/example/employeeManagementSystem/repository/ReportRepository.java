package com.example.employeeManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.employeeManagementSystem.entity.Employee;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Employee, Long> {

    @Query(value = """
            SELECT
                e.id,
                CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
                e.designation,
                d.name AS department
            FROM employee e
            INNER JOIN department d
                ON e.department_id = d.id
            """, nativeQuery = true)
    List<Object[]> getEmployeesWithDepartments();


    @Query(value = """
            SELECT
                d.id,
                d.name,
                COUNT(e.id) AS employee_count
            FROM department d
            LEFT JOIN employee e
                ON d.id = e.department_id
            GROUP BY d.id, d.name
            """, nativeQuery = true)
    List<Object[]> getDepartmentSummary();


    @Query(value = """
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
            LIMIT 10
            """, nativeQuery = true)
    List<Object[]> getHighestPaidEmployees();


    @Query(value = """
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
            )
            """, nativeQuery = true)
    List<Object[]> getEmployeesAboveDepartmentAverage();


    @Query(value = """
            SELECT
                e.first_name AS employee,
                m.first_name AS manager
            FROM employee e
            LEFT JOIN employee m
                ON e.manager_id = m.id
            """, nativeQuery = true)
    List<Object[]> getEmployeeManagers();


    @Query(value = """
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
            )
            """, nativeQuery = true)
    List<Object[]> getPeople();


    @Query(value = """
            SELECT
                employee_id,
                date AS activity_date,
                'ATTENDANCE' AS activity_type,
                status AS activity_status
            FROM attendance
            WHERE employee_id = :employeeId

            UNION

            SELECT
                employee_id,
                start_date AS activity_date,
                'LEAVE' AS activity_type,
                status AS activity_status
            FROM employee_leave
            WHERE employee_id = :employeeId

            ORDER BY activity_date
            """, nativeQuery = true)
    List<Object[]> getEmployeeActivity(Long employeeId);
}