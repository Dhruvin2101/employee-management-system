package com.example.employeeManagementSystem.repository;

import com.example.employeeManagementSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DashboardRepository extends JpaRepository<Employee, Long> {

    @Query(value = """
            SELECT COUNT(*)
            FROM employee
            """, nativeQuery = true)
    Long getTotalEmployees();


    @Query(value = """
            SELECT COUNT(*)
            FROM employee
            WHERE status = 'ACTIVE'
            """, nativeQuery = true)
    Long getActiveEmployees();


    @Query(value = """
            SELECT COUNT(*)
            FROM employee
            WHERE status = 'INACTIVE'
            """, nativeQuery = true)
    Long getInactiveEmployees();


    @Query(value = """
            SELECT COUNT(*)
            FROM department
            """, nativeQuery = true)
    Long getTotalDepartments();


    @Query(value = """
            SELECT COUNT(*)
            FROM attendance
            WHERE date = CURRENT_DATE
            AND status = 'PRESENT'
            """, nativeQuery = true)
    Long getPresentToday();


    @Query(value = """
            SELECT COUNT(*)
            FROM attendance
            WHERE date = CURRENT_DATE
            AND status = 'ABSENT'
            """, nativeQuery = true)
    Long getAbsentToday();


    @Query(value = """
            SELECT COUNT(*)
            FROM employee_leave
            WHERE start_date <= CURRENT_DATE
            AND end_date >= CURRENT_DATE
            AND status = 'APPROVED'
            """, nativeQuery = true)
    Long getOnLeaveToday();


    @Query(value = """
            SELECT COUNT(*)
            FROM employee_leave
            WHERE status = 'PENDING'
            """, nativeQuery = true)
    Long getPendingLeaves();


    @Query(value = """
            SELECT COALESCE(SUM(net_salary), 0)
            FROM salary
            WHERE month = MONTH(CURRENT_DATE)
            AND year = YEAR(CURRENT_DATE)
            """, nativeQuery = true)
    BigDecimal getMonthlyPayroll();
}