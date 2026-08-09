package com.example.employeeManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    public enum Role{ADMIN, HR, MANAGER, EMPLOYEE};

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long employeeId;
}
