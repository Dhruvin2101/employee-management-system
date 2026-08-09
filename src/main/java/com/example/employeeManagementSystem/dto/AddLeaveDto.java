package com.example.employeeManagementSystem.dto;

import com.example.employeeManagementSystem.entity.Leave;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLeaveDto {

    private Long employeeId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;

    private Leave.LeaveType leaveType;
}