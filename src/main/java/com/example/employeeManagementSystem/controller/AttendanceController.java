package com.example.employeeManagementSystem.controller;

import com.example.employeeManagementSystem.dto.AddAttendanceDto;
import com.example.employeeManagementSystem.dto.AttendanceDto;
import com.example.employeeManagementSystem.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/attendance")
    public ResponseEntity<AttendanceDto> createAttendance(
            @RequestBody AddAttendanceDto addAttendanceDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(attendanceService.createAttendance(addAttendanceDto));
    }

    @GetMapping("/employees/{id}/attendance")
    public ResponseEntity<List<AttendanceDto>> getEmployeeAttendance(
            @PathVariable Long id,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        if (month != null && year != null) {

            return ResponseEntity.ok(
                    attendanceService.getMonthlyAttendance(
                            id,
                            month,
                            year
                    )
            );
        }

        return ResponseEntity.ok(
                attendanceService.getEmployeeAttendance(id)
        );
    }
}