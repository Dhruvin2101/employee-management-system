package com.example.employeeManagementSystem.service.impl;

import com.example.employeeManagementSystem.dto.AddDepartmentDto;
import com.example.employeeManagementSystem.dto.AddEmployeeDto;
import com.example.employeeManagementSystem.dto.DepartmentDto;
import com.example.employeeManagementSystem.entity.Department;
import com.example.employeeManagementSystem.repository.DepartmentRepository;
import com.example.employeeManagementSystem.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();

        return departments
                .stream()
                .map(department -> modelMapper.map(department, DepartmentDto.class))
                .toList();
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Department does not exist with Id: " + id));
        return modelMapper.map(department, DepartmentDto.class);
    }

    @Override
    public DepartmentDto createDepartment(AddDepartmentDto addDepartmentDto) {
        Department newDepartment = modelMapper.map(addDepartmentDto, Department.class);
        Department department = departmentRepository.save(newDepartment);
        return modelMapper.map(department, DepartmentDto.class);

    }

    @Override
    public DepartmentDto updateDepartment(Long id, Map<String, Object> updates) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department does not exist with Id: " + id));


        updates.forEach((fields, value) -> {

                    switch (fields) {
                        case "name":
                            department.setName((String) value);
                            break;
                        case "description":
                            department.setDescription((String) value);
                            break;
                        case "location":
                            department.setLocation((String) value);
                            break;
                        case "joiningDate":
                            department.setJoiningDate((LocalDate) value);
                            break;
                        default:
                            try {
                                throw new IllegalAccessException("Field is not supported");
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                    }
                }
        );
        Department updatedDepartment = departmentRepository.save(department);
        return modelMapper.map(department, DepartmentDto.class);
    }

    @Override
    public void deleteDepartment(Long id) throws IllegalAccessException {
        if (!departmentRepository.existsById(id)) {
            throw new IllegalAccessException("Department does not exist with Id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
