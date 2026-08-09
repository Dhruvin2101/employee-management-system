package com.example.employeeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeActivityDto {

    private Long employeeId;

    private LocalDate activityDate;

    private String activityType;

    private String activityStatus;
}