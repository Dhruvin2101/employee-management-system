package com.example.employeeManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "employee_leave")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;

    public enum LeaveType {
        CASUAL,
        SICK,
        PAID,
        UNPAID
    }

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long approvedBy;

    private LocalDateTime createdAt;
}