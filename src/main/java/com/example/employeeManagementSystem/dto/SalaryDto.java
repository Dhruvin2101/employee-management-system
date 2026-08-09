package com.example.employeeManagementSystem.dto;

import com.example.employeeManagementSystem.entity.Salary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDto {

    private Long id;

    private Long employeeId;

    private Integer month;

    private Integer year;

    private BigDecimal basicSalary;

    private BigDecimal allowance;

    private BigDecimal deduction;

    private BigDecimal netSalary;

    private Salary.PaymentStatus paymentStatus;
}