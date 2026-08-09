package com.example.employeeManagementSystem.service;

import com.example.employeeManagementSystem.dto.AddSalaryDto;
import com.example.employeeManagementSystem.dto.SalaryDto;

import java.util.List;

public interface SalaryService {

    SalaryDto generateSalary(AddSalaryDto addSalaryDto);

    List<SalaryDto> getSalaryHistory(Long employeeId);
}