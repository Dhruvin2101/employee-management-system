package com.example.employeeManagementSystem.service.impl;

import com.example.employeeManagementSystem.dto.AddUserDto;
import com.example.employeeManagementSystem.dto.UserDto;
import com.example.employeeManagementSystem.entity.Employee;
import com.example.employeeManagementSystem.entity.User;
import com.example.employeeManagementSystem.repository.EmployeeRepository;
import com.example.employeeManagementSystem.repository.UserRepository;
import com.example.employeeManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found with ID: " + id));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto createNewUser(AddUserDto addUserDto) {

        // Check employee exists
        Employee employee = employeeRepository.findById(addUserDto.getEmployeeId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found with ID: " + addUserDto.getEmployeeId()
                        ));

        // Check username already exists
        if (userRepository.existsByUsername(addUserDto.getUsername())) {
            throw new IllegalArgumentException(
                    "Username already exists: " + addUserDto.getUsername()
            );
        }

        User newUser = modelMapper.map(addUserDto, User.class);

        User user = userRepository.save(newUser);

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUserById(Long id) throws IllegalAccessException {

        if (!userRepository.existsById(id)) {
            throw new IllegalAccessException(
                    "User does not exist with ID: " + id
            );
        }

        userRepository.deleteById(id);
    }
}