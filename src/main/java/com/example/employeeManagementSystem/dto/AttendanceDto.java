package com.example.employeeManagementSystem.dto;

import com.example.employeeManagementSystem.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {

    private Long id;

    private Long employeeId;

    private LocalDate date;

    private LocalTime checkIn;

    private LocalTime checkOut;

    private Attendance.Status status;

    private BigDecimal workingHours;
}