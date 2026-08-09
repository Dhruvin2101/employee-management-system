package com.example.employeeManagementSystem.service;

import com.example.employeeManagementSystem.dto.AddLeaveDto;
import com.example.employeeManagementSystem.dto.LeaveDto;

import java.util.List;

public interface LeaveService {

    LeaveDto createLeave(AddLeaveDto addLeaveDto);

    LeaveDto approveLeave(Long id, Long approvedBy);

    LeaveDto rejectLeave(Long id);

    LeaveDto getLeaveById(Long id);

    List<LeaveDto> getAllLeaves();

    List<LeaveDto> getEmployeeLeaves(Long employeeId);
}