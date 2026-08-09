package com.example.employeeManagementSystem.service.impl;

import com.example.employeeManagementSystem.dto.*;
import com.example.employeeManagementSystem.repository.ReportRepository;
import com.example.employeeManagementSystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public List<EmployeeDepartmentDto> getEmployeesWithDepartments() {

        return reportRepository.getEmployeesWithDepartments()
                .stream()
                .map(row -> new EmployeeDepartmentDto(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (String) row[3]
                ))
                .toList();
    }

    @Override
    public List<DepartmentSummaryDto> getDepartmentSummary() {

        return reportRepository.getDepartmentSummary()
                .stream()
                .map(row -> new DepartmentSummaryDto(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue()
                ))
                .toList();
    }

    @Override
    public List<HighestPaidEmployeeDto> getHighestPaidEmployees() {

        return reportRepository.getHighestPaidEmployees()
                .stream()
                .map(row -> new HighestPaidEmployeeDto(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (BigDecimal) row[3],
                        (String) row[4]
                ))
                .toList();
    }

    @Override
    public List<EmployeeAboveAverageDto> getEmployeesAboveDepartmentAverage() {

        return reportRepository.getEmployeesAboveDepartmentAverage()
                .stream()
                .map(row -> new EmployeeAboveAverageDto(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (BigDecimal) row[3],
                        (String) row[4]
                ))
                .toList();
    }

    @Override
    public List<EmployeeManagerDto> getEmployeeManagers() {

        return reportRepository.getEmployeeManagers()
                .stream()
                .map(row -> new EmployeeManagerDto(
                        (String) row[0],
                        (String) row[1]
                ))
                .toList();
    }

    @Override
    public List<PersonReportDto> getPeople() {

        return reportRepository.getPeople()
                .stream()
                .map(row -> new PersonReportDto(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (String) row[3]
                ))
                .toList();
    }

    @Override
    public List<EmployeeActivityDto> getEmployeeActivity(Long employeeId) {

        return reportRepository.getEmployeeActivity(employeeId)
                .stream()
                .map(row -> new EmployeeActivityDto(
                        ((Number) row[0]).longValue(),
                        ((java.sql.Date) row[1]).toLocalDate(),
                        (String) row[2],
                        (String) row[3]
                ))
                .toList();
    }
}