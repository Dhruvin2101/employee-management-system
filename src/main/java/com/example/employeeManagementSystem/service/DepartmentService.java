package com.example.employeeManagementSystem.service;

import com.example.employeeManagementSystem.dto.AddDepartmentDto;
import com.example.employeeManagementSystem.dto.AddEmployeeDto;
import com.example.employeeManagementSystem.dto.DepartmentDto;

import java.util.List;
import java.util.Map;

public interface DepartmentService {

    List<DepartmentDto> getAllDepartments();

    DepartmentDto getDepartmentById(Long id);

    DepartmentDto createDepartment(AddDepartmentDto addDepartmentDto);

    DepartmentDto updateDepartment(Long id, Map<String, Object> updates);

    void deleteDepartment(Long id) throws IllegalAccessException;
}
