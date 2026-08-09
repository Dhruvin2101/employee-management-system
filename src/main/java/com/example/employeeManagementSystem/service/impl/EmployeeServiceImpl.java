package com.example.employeeManagementSystem.service.impl;

import com.example.employeeManagementSystem.dto.AddEmployeeDto;
import com.example.employeeManagementSystem.dto.EmployeeDto;
import com.example.employeeManagementSystem.entity.Employee;
import com.example.employeeManagementSystem.repository.EmployeeRepository;
import com.example.employeeManagementSystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .toList();
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto createNewEmployee(AddEmployeeDto addEmployeeDto) {
        Employee newEmployee = modelMapper.map(addEmployeeDto, Employee.class);
        Employee employee = employeeRepository.save(newEmployee);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployeeById(Long id) throws IllegalAccessException {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalAccessException("Employee does not exists with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, Map<String, Object> updates) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));

        updates.forEach((field, value) -> {
            switch (field) {
                case "firstName":
                    employee.setFirstName((String) value);
                    break;
                case "lastName":
                    employee.setLastName((String) value);
                    break;
                case "email":
                    employee.setEmail((String) value);
                    break;
                case "phone":
                    employee.setPhone((String) value);
                    break;
                case "dateOfBirth":
                    employee.setDateOfBirth((LocalDate) value);
                    break;
                case "joiningDate":
                    employee.setJoiningDate((LocalDate) value);
                    break;
                case "designation":
                    employee.setDesignation((String) value);
                    break;
                case "salary":
                    employee.setSalary((BigDecimal) value);
                    break;
                case "status":
                    employee.setStatus((Employee.EmployeeStatus) value);
                    break;
                case "departmentId":
                    employee.setDepartmentId((Long) value);
                    break;
                case "managerId":
                    employee.setDesignation((String) value);
                    break;
                default:
                    try {
                        throw new IllegalAccessException("Field is not Supported");
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

            }
        });
        Employee updatedEmployee = employeeRepository.save(employee);
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }


}















