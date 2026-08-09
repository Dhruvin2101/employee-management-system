package com.example.employeeManagementSystem.service;

import com.example.employeeManagementSystem.dto.AddUserDto;
import com.example.employeeManagementSystem.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto createNewUser(AddUserDto addUserDto);

    void deleteUserById(Long id) throws IllegalAccessException;
}