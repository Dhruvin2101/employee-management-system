package com.example.employeeManagementSystem.service;

import com.example.employeeManagementSystem.dto.*;

import java.util.List;

public interface ReportService {

    List<EmployeeDepartmentDto> getEmployeesWithDepartments();

    List<DepartmentSummaryDto> getDepartmentSummary();

    List<HighestPaidEmployeeDto> getHighestPaidEmployees();

    List<EmployeeAboveAverageDto> getEmployeesAboveDepartmentAverage();

    List<EmployeeManagerDto> getEmployeeManagers();

    List<PersonReportDto> getPeople();

    List<EmployeeActivityDto> getEmployeeActivity(Long employeeId);
}