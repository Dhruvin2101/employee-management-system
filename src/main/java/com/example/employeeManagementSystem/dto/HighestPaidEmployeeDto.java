package com.example.employeeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HighestPaidEmployeeDto {

    private Long id;

    private String firstName;

    private String lastName;

    private BigDecimal salary;

    private String department;
}