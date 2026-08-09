package com.example.employeeManagementSystem.repository;

import com.example.employeeManagementSystem.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    boolean existsByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long employeeId,
            LocalDate endDate,
            LocalDate startDate
    );

    List<Leave> findByEmployeeIdOrderByStartDateAsc(Long employeeId);
}