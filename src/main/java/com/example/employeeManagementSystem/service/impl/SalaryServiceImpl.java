package com.example.employeeManagementSystem.service.impl;

import com.example.employeeManagementSystem.dto.AddSalaryDto;
import com.example.employeeManagementSystem.dto.SalaryDto;
import com.example.employeeManagementSystem.entity.Employee;
import com.example.employeeManagementSystem.entity.Salary;
import com.example.employeeManagementSystem.repository.EmployeeRepository;
import com.example.employeeManagementSystem.repository.SalaryRepository;
import com.example.employeeManagementSystem.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public SalaryDto generateSalary(AddSalaryDto addSalaryDto) {

        // Check employee exists
        Employee employee = employeeRepository.findById(addSalaryDto.getEmployeeId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found with ID: " + addSalaryDto.getEmployeeId()
                        )
                );

        // Check employee is active
        if (employee.getStatus() != Employee.EmployeeStatus.ACTIVE) {
            throw new IllegalArgumentException(
                    "Salary cannot be generated because employee is not active"
            );
        }

        // Check month is valid
        if (addSalaryDto.getMonth() < 1 || addSalaryDto.getMonth() > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }

        // Check salary hasn't already been generated
        if (salaryRepository.existsByEmployeeIdAndMonthAndYear(
                addSalaryDto.getEmployeeId(),
                addSalaryDto.getMonth(),
                addSalaryDto.getYear()
        )) {
            throw new IllegalArgumentException(
                    "Salary already generated for employee ID: "
                            + addSalaryDto.getEmployeeId()
                            + " for "
                            + addSalaryDto.getMonth()
                            + "/"
                            + addSalaryDto.getYear()
            );
        }

        // Get employee's basic salary
        BigDecimal basicSalary = employee.getSalary();

        if (basicSalary == null) {
            throw new IllegalArgumentException(
                    "Employee does not have a salary assigned"
            );
        }

        // Currently allowance and deduction are 0
        BigDecimal allowance = BigDecimal.ZERO;
        BigDecimal deduction = BigDecimal.ZERO;

        // Calculate net salary
        BigDecimal netSalary = basicSalary
                .add(allowance)
                .subtract(deduction);

        // Create Salary entity
        Salary salary = new Salary();

        salary.setEmployeeId(employee.getId());
        salary.setMonth(addSalaryDto.getMonth());
        salary.setYear(addSalaryDto.getYear());
        salary.setBasicSalary(basicSalary);
        salary.setAllowance(allowance);
        salary.setDeduction(deduction);
        salary.setNetSalary(netSalary);
        salary.setPaymentStatus(Salary.PaymentStatus.PENDING);

        // Save salary
        Salary savedSalary = salaryRepository.save(salary);

        return modelMapper.map(savedSalary, SalaryDto.class);
    }

    @Override
    public List<SalaryDto> getSalaryHistory(Long employeeId) {

        // Check employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new IllegalArgumentException(
                    "Employee not found with ID: " + employeeId
            );
        }

        List<Salary> salaries =
                salaryRepository.findByEmployeeIdOrderByYearDescMonthDesc(employeeId);

        return salaries
                .stream()
                .map(salary -> modelMapper.map(salary, SalaryDto.class))
                .toList();
    }
}