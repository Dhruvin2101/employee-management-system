package com.example.employeeManagementSystem.repository;

import com.example.employeeManagementSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// we have to tell which entity should it connect with and the type of id of the entity

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
