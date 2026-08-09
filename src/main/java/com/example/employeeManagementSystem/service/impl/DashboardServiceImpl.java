package com.example.employeeManagementSystem.service.impl;

import com.example.employeeManagementSystem.dto.DashboardDto;
import com.example.employeeManagementSystem.repository.DashboardRepository;
import com.example.employeeManagementSystem.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    @Override
    public DashboardDto getDashboard() {

        Long totalEmployees =
                dashboardRepository.getTotalEmployees();

        Long activeEmployees =
                dashboardRepository.getActiveEmployees();

        Long inactiveEmployees =
                dashboardRepository.getInactiveEmployees();

        Long totalDepartments =
                dashboardRepository.getTotalDepartments();

        Long presentToday =
                dashboardRepository.getPresentToday();

        Long absentToday =
                dashboardRepository.getAbsentToday();

        Long onLeaveToday =
                dashboardRepository.getOnLeaveToday();

        Long pendingLeaves =
                dashboardRepository.getPendingLeaves();

        BigDecimal monthlyPayroll =
                dashboardRepository.getMonthlyPayroll();

        return new DashboardDto(
                totalEmployees,
                activeEmployees,
                inactiveEmployees,
                totalDepartments,
                presentToday,
                absentToday,
                onLeaveToday,
                pendingLeaves,
                monthlyPayroll
        );
    }
}