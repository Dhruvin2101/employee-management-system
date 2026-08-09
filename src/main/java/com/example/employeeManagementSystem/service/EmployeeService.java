package com.example.employeeManagementSystem.service;

import com.example.employeeManagementSystem.dto.AddEmployeeDto;
import com.example.employeeManagementSystem.dto.EmployeeDto;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto  createNewEmployee( AddEmployeeDto addEmployeeDto);

    void deleteEmployeeById(Long id) throws IllegalAccessException;

    EmployeeDto updateEmployee(Long id, Map<String, Object> updates);
}
