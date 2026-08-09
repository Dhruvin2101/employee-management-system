package com.example.employeeManagementSystem.controller;

import com.example.employeeManagementSystem.dto.AddSalaryDto;
import com.example.employeeManagementSystem.dto.SalaryDto;
import com.example.employeeManagementSystem.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping("/salaries/generate")
    public ResponseEntity<SalaryDto> generateSalary(
            @RequestBody AddSalaryDto addSalaryDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(salaryService.generateSalary(addSalaryDto));
    }

    @GetMapping("/employees/{id}/salaries")
    public ResponseEntity<List<SalaryDto>> getSalaryHistory(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                salaryService.getSalaryHistory(id)
        );
    }
}