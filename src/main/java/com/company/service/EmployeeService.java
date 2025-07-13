package com.company.service;

import com.company.entity.Employee;
import com.company.entity.FullTimeEmployee;
import com.company.entity.PartTimeEmployee;
import com.company.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Employee CRUD operations
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Employee with email '" + employee.getEmail() + "' already exists");
        }
        
        if (employee.getDepartment() == null || employee.getDepartment().getId() == null) {
            throw new IllegalArgumentException("Employee must be associated with a department");
        }
        
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
        
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        employee.setHireDate(employeeDetails.getHireDate());
        employee.setSalary(employeeDetails.getSalary());
        employee.setJobTitle(employeeDetails.getJobTitle());
        
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // Full-time employee operations
    public FullTimeEmployee createFullTimeEmployee(FullTimeEmployee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Employee with email '" + employee.getEmail() + "' already exists");
        }
        
        if (employee.getDepartment() == null || employee.getDepartment().getId() == null) {
            throw new IllegalArgumentException("Employee must be associated with a department");
        }
        
        return employeeRepository.save(employee);
    }

    public List<FullTimeEmployee> getAllFullTimeEmployees() {
        return employeeRepository.findAllFullTimeEmployees();
    }

    public List<FullTimeEmployee> getFullTimeEmployeesByMinimumBonus(BigDecimal minBonus) {
        return employeeRepository.findFullTimeEmployeesByMinimumBonus(minBonus);
    }

    // Part-time employee operations
    public PartTimeEmployee createPartTimeEmployee(PartTimeEmployee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Employee with email '" + employee.getEmail() + "' already exists");
        }
        
        if (employee.getDepartment() == null || employee.getDepartment().getId() == null) {
            throw new IllegalArgumentException("Employee must be associated with a department");
        }
        
        return employeeRepository.save(employee);
    }

    public List<PartTimeEmployee> getAllPartTimeEmployees() {
        return employeeRepository.findAllPartTimeEmployees();
    }

    public List<PartTimeEmployee> getPartTimeEmployeesByMinimumHours(Integer minHours) {
        return employeeRepository.findPartTimeEmployeesByMinimumHours(minHours);
    }

    public List<PartTimeEmployee> getPartTimeEmployeesWithExpiredContracts() {
        return employeeRepository.findPartTimeEmployeesWithExpiredContracts(LocalDate.now());
    }

    // Business logic operations
    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    public List<Employee> getEmployeesByCompanyId(Long companyId) {
        return employeeRepository.findEmployeesByCompanyId(companyId);
    }

    public List<Employee> searchEmployeesByName(String firstName, String lastName) {
        return employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName);
    }

    public List<Employee> getEmployeesByHireDateRange(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findByHireDateBetween(startDate, endDate);
    }

    public List<Employee> getEmployeesBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        return employeeRepository.findEmployeesBySalaryRange(minSalary, maxSalary);
    }

    public List<Employee> getEmployeesByDepartmentOrderedBySalary(Long departmentId) {
        return employeeRepository.findEmployeesByDepartmentOrderedBySalary(departmentId);
    }

    public List<Employee> getEmployeesWithSalaryGreaterThan(BigDecimal salary) {
        return employeeRepository.findBySalaryGreaterThan(salary);
    }

    public boolean employeeExists(Long id) {
        return employeeRepository.existsById(id);
    }

    public boolean employeeExistsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public int getEmployeeCountByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).size();
    }

    public int getEmployeeCountByCompanyId(Long companyId) {
        return employeeRepository.findEmployeesByCompanyId(companyId).size();
    }
}