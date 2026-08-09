package com.example.employeeManagementSystem.entity;

import com.example.employeeManagementSystem.dto.EmployeeDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private LocalDate dateOfBirth;
    private LocalDate joiningDate;

    private String designation;

    private BigDecimal salary;

    public enum EmployeeStatus{ACTIVE,INACTIVE,ON_LEAVE,TERMINATED,RESIGNED}
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    private Long departmentId;
    private Long managerId;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
