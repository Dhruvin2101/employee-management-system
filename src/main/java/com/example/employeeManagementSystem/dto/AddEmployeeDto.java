package com.example.employeeManagementSystem.dto;

import com.example.employeeManagementSystem.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddEmployeeDto {
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private LocalDate dateOfBirth;
        private LocalDate joiningDate;
        private String designation;
        private BigDecimal salary;
        private Employee.EmployeeStatus status;
        private Long departmentId;
        private Long managerId;
}
