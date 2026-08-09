package com.example.employeeManagementSystem.repository;

import com.example.employeeManagementSystem.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByEmployeeIdAndDate(Long employeeId, LocalDate date);

    List<Attendance> findByEmployeeIdOrderByDate(Long employeeId);

    List<Attendance> findByEmployeeIdAndDateBetweenOrderByDate(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate
    );
}