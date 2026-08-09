package com.example.employeeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDto {

    private Long totalEmployees;

    private Long activeEmployees;

    private Long inactiveEmployees;

    private Long totalDepartments;

    private Long presentToday;

    private Long absentToday;

    private Long onLeaveToday;

    private Long pendingLeaves;

    private BigDecimal monthlyPayroll;
}