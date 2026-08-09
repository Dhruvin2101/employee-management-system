package com.example.employeeManagementSystem.service;

import com.example.employeeManagementSystem.dto.AddAttendanceDto;
import com.example.employeeManagementSystem.dto.AttendanceDto;

import java.util.List;

public interface AttendanceService {

    AttendanceDto createAttendance(AddAttendanceDto addAttendanceDto);

    List<AttendanceDto> getEmployeeAttendance(Long employeeId);

    List<AttendanceDto> getMonthlyAttendance(
            Long employeeId,
            int month,
            int year
    );
}