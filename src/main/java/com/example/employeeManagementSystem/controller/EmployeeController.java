package com.example.employeeManagementSystem.controller;

import com.example.employeeManagementSystem.dto.AddEmployeeDto;
import com.example.employeeManagementSystem.dto.EmployeeDto;
import com.example.employeeManagementSystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody AddEmployeeDto addEmployeeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createNewEmployee(addEmployeeDto));
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) throws IllegalAccessException {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("employee/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.updateEmployee(id, updates));
    }

}
