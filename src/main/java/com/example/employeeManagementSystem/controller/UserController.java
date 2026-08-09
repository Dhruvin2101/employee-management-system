package com.example.employeeManagementSystem.controller;

import com.example.employeeManagementSystem.dto.AddUserDto;
import com.example.employeeManagementSystem.dto.UserDto;
import com.example.employeeManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/user")
    public ResponseEntity<UserDto> createNewUser(
            @RequestBody AddUserDto addUserDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createNewUser(addUserDto));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) throws IllegalAccessException {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}