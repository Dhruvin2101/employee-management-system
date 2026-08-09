package com.example.employeeManagementSystem.dto;

import com.example.employeeManagementSystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDto {

    private String username;

    private String password;

    private User.Role role;

    private Long employeeId;
}