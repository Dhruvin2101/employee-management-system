package com.example.employeeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonReportDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String type;
}