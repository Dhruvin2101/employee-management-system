package com.example.employeeManagementSystem.controller;

import com.example.employeeManagementSystem.dto.AddLeaveDto;
import com.example.employeeManagementSystem.dto.LeaveDto;
import com.example.employeeManagementSystem.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;


    // Apply for leave
    @PostMapping("/leaves")
    public ResponseEntity<LeaveDto> createLeave(
            @RequestBody AddLeaveDto addLeaveDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(leaveService.createLeave(addLeaveDto));
    }


    // Get all leaves
    @GetMapping("/leaves")
    public ResponseEntity<List<LeaveDto>> getAllLeaves() {

        return ResponseEntity.ok(
                leaveService.getAllLeaves()
        );
    }


    // Get leave by ID
    @GetMapping("/leaves/{id}")
    public ResponseEntity<LeaveDto> getLeaveById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                leaveService.getLeaveById(id)
        );
    }


    // Get all leaves of an employee
    @GetMapping("/employees/{employeeId}/leaves")
    public ResponseEntity<List<LeaveDto>> getEmployeeLeaves(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                leaveService.getEmployeeLeaves(employeeId)
        );
    }


    // Approve leave
    @PutMapping("/leaves/{id}/approve")
    public ResponseEntity<LeaveDto> approveLeave(
            @PathVariable Long id,
            @RequestParam Long approvedBy) {

        return ResponseEntity.ok(
                leaveService.approveLeave(id, approvedBy)
        );
    }


    // Reject leave
    @PutMapping("/leaves/{id}/reject")
    public ResponseEntity<LeaveDto> rejectLeave(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                leaveService.rejectLeave(id)
        );
    }
}