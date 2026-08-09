package com.example.employeeManagementSystem.dto;

import com.example.employeeManagementSystem.entity.Leave;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDto {

    private Long id;

    private Long employeeId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;

    private Leave.LeaveType leaveType;

    private Leave.Status status;

    private Long approvedBy;

    private LocalDateTime createdAt;
}