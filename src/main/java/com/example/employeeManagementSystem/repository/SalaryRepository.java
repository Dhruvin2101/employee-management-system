package com.example.employeeManagementSystem.repository;

import com.example.employeeManagementSystem.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    boolean existsByEmployeeIdAndMonthAndYear(Long employeeId, Integer month, Integer year);

    List<Salary> findByEmployeeIdOrderByYearDescMonthDesc(Long employeeId);
}