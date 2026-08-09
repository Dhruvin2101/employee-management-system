package com.example.employeeManagementSystem.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

        private Long id;
        private String name;
        private String description;
        private String location;
        private LocalDate joiningDate;


}
