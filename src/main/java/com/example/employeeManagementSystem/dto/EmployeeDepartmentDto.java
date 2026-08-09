package com.example.employeeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDepartmentDto {

    private Long id;

    private String employeeName;

    private String designation;

    private String department;
}