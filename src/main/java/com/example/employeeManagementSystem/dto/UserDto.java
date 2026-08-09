package com.example.employeeManagementSystem.dto;

import com.example.employeeManagementSystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private User.Role role;

    private Long employeeId;
}