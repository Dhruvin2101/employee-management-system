package com.example.employeeManagementSystem.config;

import com.example.employeeManagementSystem.dto.AddEmployeeDto;
import com.example.employeeManagementSystem.dto.AddLeaveDto;
import com.example.employeeManagementSystem.entity.Employee;
import com.example.employeeManagementSystem.entity.Leave;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class mapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(AddEmployeeDto.class, Employee.class)
                .addMappings(mapper -> {
                    mapper.skip(Employee::setId);
                    mapper.skip(Employee::setCreatedAt);
                    mapper.skip(Employee::setUpdatedAt);
                });

        modelMapper.typeMap(AddLeaveDto.class, Leave.class)
                .addMappings(mapper -> {
                    mapper.skip(Leave::setId);
                    mapper.skip(Leave::setStatus);
                    mapper.skip(Leave::setApprovedBy);
                    mapper.skip(Leave::setCreatedAt);
                });

        return modelMapper;
    }
}