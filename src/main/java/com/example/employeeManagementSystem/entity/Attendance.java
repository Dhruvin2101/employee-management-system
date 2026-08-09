package com.example.employeeManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Attendance     {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private LocalDate date;

    private LocalTime checkIn;

    private LocalTime checkOut;

    public enum Status{PRESENT, ABSENT,HALF_DAY, WORK_FROM_HOME};

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal workingHours;
}
