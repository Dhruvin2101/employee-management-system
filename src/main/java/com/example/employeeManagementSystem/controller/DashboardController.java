package com.example.employeeManagementSystem.controller;

import com.example.employeeManagementSystem.dto.DashboardDto;
import com.example.employeeManagementSystem.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDto> getDashboard() {

        return ResponseEntity.ok(
                dashboardService.getDashboard()
        );
    }
}