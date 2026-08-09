package com.example.employeeManagementSystem.controller;

import com.example.employeeManagementSystem.dto.*;
import com.example.employeeManagementSystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports/employees-with-departments")
    public ResponseEntity<List<EmployeeDepartmentDto>> getEmployeesWithDepartments() {
        return ResponseEntity.ok(
                reportService.getEmployeesWithDepartments()
        );
    }

    @GetMapping("/reports/department-summary")
    public ResponseEntity<List<DepartmentSummaryDto>> getDepartmentSummary() {
        return ResponseEntity.ok(
                reportService.getDepartmentSummary()
        );
    }

    @GetMapping("/reports/highest-paid")
    public ResponseEntity<List<HighestPaidEmployeeDto>> getHighestPaidEmployees() {
        return ResponseEntity.ok(
                reportService.getHighestPaidEmployees()
        );
    }

    @GetMapping("/reports/employees-above-department-average")
    public ResponseEntity<List<EmployeeAboveAverageDto>>
    getEmployeesAboveDepartmentAverage() {

        return ResponseEntity.ok(
                reportService.getEmployeesAboveDepartmentAverage()
        );
    }

    @GetMapping("/reports/employee-managers")
    public ResponseEntity<List<EmployeeManagerDto>> getEmployeeManagers() {
        return ResponseEntity.ok(
                reportService.getEmployeeManagers()
        );
    }

    @GetMapping("/reports/people")
    public ResponseEntity<List<PersonReportDto>> getPeople() {
        return ResponseEntity.ok(
                reportService.getPeople()
        );
    }

    @GetMapping("/reports/employee-activity/{employeeId}")
    public ResponseEntity<List<EmployeeActivityDto>> getEmployeeActivity(
            @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(
                reportService.getEmployeeActivity(employeeId)
        );
    }
}