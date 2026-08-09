package com.example.employeeManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private Integer month;

    private Integer year;

    private BigDecimal basicSalary;

    private BigDecimal allowance;

    private BigDecimal deduction;

    private BigDecimal netSalary;

    public enum PaymentStatus {
        PENDING,
        PAID,
        FAILED
    }

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}