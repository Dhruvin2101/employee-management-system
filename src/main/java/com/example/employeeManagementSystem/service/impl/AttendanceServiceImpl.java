package com.example.employeeManagementSystem.service.impl;

import com.example.employeeManagementSystem.dto.AddAttendanceDto;
import com.example.employeeManagementSystem.dto.AttendanceDto;
import com.example.employeeManagementSystem.entity.Attendance;
import com.example.employeeManagementSystem.entity.Employee;
import com.example.employeeManagementSystem.repository.AttendanceRepository;
import com.example.employeeManagementSystem.repository.EmployeeRepository;
import com.example.employeeManagementSystem.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public AttendanceDto createAttendance(AddAttendanceDto addAttendanceDto) {

        Employee employee = employeeRepository.findById(addAttendanceDto.getEmployeeId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found with ID: "
                                        + addAttendanceDto.getEmployeeId()
                        )
                );

        if (employee.getStatus() != Employee.EmployeeStatus.ACTIVE) {
            throw new IllegalArgumentException(
                    "Employee is not active"
            );
        }

        if (attendanceRepository.existsByEmployeeIdAndDate(
                addAttendanceDto.getEmployeeId(),
                addAttendanceDto.getDate())) {

            throw new IllegalArgumentException(
                    "Attendance already exists for employee ID: "
                            + addAttendanceDto.getEmployeeId()
                            + " on "
                            + addAttendanceDto.getDate()
            );
        }

        Duration duration = Duration.between(
                addAttendanceDto.getCheckIn(),
                addAttendanceDto.getCheckOut()
        );

        if (duration.isNegative() || duration.isZero()) {
            throw new IllegalArgumentException(
                    "Check-out time must be after check-in time"
            );
        }

        BigDecimal workingHours = BigDecimal.valueOf(
                duration.toMinutes() / 60.0
        ).setScale(2, RoundingMode.HALF_UP);

        Attendance.Status status;

        if (workingHours.compareTo(BigDecimal.valueOf(8)) >= 0) {
            status = Attendance.Status.PRESENT;
        } else if (workingHours.compareTo(BigDecimal.valueOf(4)) >= 0) {
            status = Attendance.Status.HALF_DAY;
        } else {
            status = Attendance.Status.ABSENT;
        }

        Attendance newAttendance =
                modelMapper.map(addAttendanceDto, Attendance.class);

        newAttendance.setWorkingHours(workingHours);
        newAttendance.setStatus(status);

        Attendance attendance =
                attendanceRepository.save(newAttendance);

        return modelMapper.map(attendance, AttendanceDto.class);
    }

    @Override
    public List<AttendanceDto> getEmployeeAttendance(Long employeeId) {

        if (!employeeRepository.existsById(employeeId)) {
            throw new IllegalArgumentException(
                    "Employee not found with ID: " + employeeId
            );
        }

        List<Attendance> attendanceList =
                attendanceRepository.findByEmployeeIdOrderByDate(employeeId);

        return attendanceList
                .stream()
                .map(attendance ->
                        modelMapper.map(attendance, AttendanceDto.class)
                )
                .toList();
    }

    @Override
    public List<AttendanceDto> getMonthlyAttendance(
            Long employeeId,
            int month,
            int year) {

        if (!employeeRepository.existsById(employeeId)) {
            throw new IllegalArgumentException(
                    "Employee not found with ID: " + employeeId
            );
        }

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(
                    "Month must be between 1 and 12"
            );
        }

        LocalDate startDate = LocalDate.of(year, month, 1);

        LocalDate endDate = startDate.withDayOfMonth(
                startDate.lengthOfMonth()
        );

        List<Attendance> attendanceList =
                attendanceRepository
                        .findByEmployeeIdAndDateBetweenOrderByDate(
                                employeeId,
                                startDate,
                                endDate
                        );

        return attendanceList
                .stream()
                .map(attendance ->
                        modelMapper.map(attendance, AttendanceDto.class)
                )
                .toList();
    }
}