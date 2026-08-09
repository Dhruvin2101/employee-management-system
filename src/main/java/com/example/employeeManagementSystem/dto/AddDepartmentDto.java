package com.example.employeeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddDepartmentDto {
    private String name;
    private String description;
    private String location;
    private LocalDate joiningDate;
}
