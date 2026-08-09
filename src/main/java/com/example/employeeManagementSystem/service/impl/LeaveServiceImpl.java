package com.example.employeeManagementSystem.service.impl;

import com.example.employeeManagementSystem.dto.AddLeaveDto;
import com.example.employeeManagementSystem.dto.LeaveDto;
import com.example.employeeManagementSystem.entity.Employee;
import com.example.employeeManagementSystem.entity.Leave;
import com.example.employeeManagementSystem.repository.EmployeeRepository;
import com.example.employeeManagementSystem.repository.LeaveRepository;
import com.example.employeeManagementSystem.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    @Override
    public LeaveDto createLeave(AddLeaveDto addLeaveDto) {

        // 1. Check employee exists
        Employee employee = employeeRepository.findById(addLeaveDto.getEmployeeId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found with ID: " + addLeaveDto.getEmployeeId()
                        )
                );


        // 2. Check employee is active
        if (employee.getStatus() != Employee.EmployeeStatus.ACTIVE) {
            throw new IllegalArgumentException(
                    "Employee is not active and cannot apply for leave"
            );
        }


        // 3. Check start date <= end date
        if (addLeaveDto.getStartDate() == null ||
                addLeaveDto.getEndDate() == null) {

            throw new IllegalArgumentException(
                    "Start date and end date are required"
            );
        }

        if (addLeaveDto.getStartDate().isAfter(addLeaveDto.getEndDate())) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date"
            );
        }


        // 4. Check existing leave during same/overlapping dates
        boolean leaveAlreadyExists =
                leaveRepository
                        .existsByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                                addLeaveDto.getEmployeeId(),
                                addLeaveDto.getEndDate(),
                                addLeaveDto.getStartDate()
                        );

        if (leaveAlreadyExists) {
            throw new IllegalArgumentException(
                    "Employee already has leave during the requested dates"
            );
        }


        // 5. Create leave
        Leave newLeave = modelMapper.map(addLeaveDto, Leave.class);

        // New leaves are always PENDING
        newLeave.setStatus(Leave.Status.PENDING);

        // Created automatically by backend
        newLeave.setCreatedAt(LocalDateTime.now());

        // No approver yet
        newLeave.setApprovedBy(null);


        // 6. Save
        Leave savedLeave = leaveRepository.save(newLeave);


        // 7. Return LeaveDto
        return modelMapper.map(savedLeave, LeaveDto.class);
    }


    @Override
    public LeaveDto approveLeave(Long id, Long approvedBy) {

        // 1. Find leave
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Leave not found with ID: " + id
                        )
                );


        // 2. Leave must be PENDING
        if (leave.getStatus() != Leave.Status.PENDING) {
            throw new IllegalArgumentException(
                    "Only pending leave can be approved"
            );
        }


        // 3. Check approver exists
        Employee approver = employeeRepository.findById(approvedBy)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Approver not found with ID: " + approvedBy
                        )
                );


        // 4. Check approver is active
        if (approver.getStatus() != Employee.EmployeeStatus.ACTIVE) {
            throw new IllegalArgumentException(
                    "Approver is not active"
            );
        }


        // 5. Set approved status
        leave.setStatus(Leave.Status.APPROVED);

        // 6. Store who approved it
        leave.setApprovedBy(approvedBy);


        // 7. Save
        Leave approvedLeave = leaveRepository.save(leave);


        // 8. Return LeaveDto
        return modelMapper.map(approvedLeave, LeaveDto.class);
    }


    @Override
    public LeaveDto rejectLeave(Long id) {

        // 1. Find leave
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Leave not found with ID: " + id
                        )
                );


        // 2. Leave must be PENDING
        if (leave.getStatus() != Leave.Status.PENDING) {
            throw new IllegalArgumentException(
                    "Only pending leave can be rejected"
            );
        }


        // 3. Set rejected
        leave.setStatus(Leave.Status.REJECTED);


        // 4. Save
        Leave rejectedLeave = leaveRepository.save(leave);


        // 5. Return LeaveDto
        return modelMapper.map(rejectedLeave, LeaveDto.class);
    }


    @Override
    public LeaveDto getLeaveById(Long id) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Leave not found with ID: " + id
                        )
                );

        return modelMapper.map(leave, LeaveDto.class);
    }


    @Override
    public List<LeaveDto> getAllLeaves() {

        List<Leave> leaves = leaveRepository.findAll();

        return leaves
                .stream()
                .map(leave -> modelMapper.map(leave, LeaveDto.class))
                .toList();
    }


    @Override
    public List<LeaveDto> getEmployeeLeaves(Long employeeId) {

        // Check employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new IllegalArgumentException(
                    "Employee not found with ID: " + employeeId
            );
        }

        List<Leave> leaves =
                leaveRepository.findByEmployeeIdOrderByStartDateAsc(employeeId);

        return leaves
                .stream()
                .map(leave -> modelMapper.map(leave, LeaveDto.class))
                .toList();
    }
}