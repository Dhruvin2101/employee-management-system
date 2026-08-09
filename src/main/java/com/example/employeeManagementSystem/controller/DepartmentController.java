package com.example.employeeManagementSystem.controller;

import com.example.employeeManagementSystem.dto.AddDepartmentDto;
import com.example.employeeManagementSystem.dto.AddEmployeeDto;
import com.example.employeeManagementSystem.dto.DepartmentDto;
import com.example.employeeManagementSystem.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("departments")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getAllDepartments());
    }

    @GetMapping("department/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getDepartmentById(id));
    }

    @PostMapping("department")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody AddDepartmentDto addDepartmentDto) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.createDepartment(addDepartmentDto));
    }

    @PatchMapping("department/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.updateDepartment(id, updates));
    }

    @DeleteMapping("department/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) throws IllegalAccessException {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

}
