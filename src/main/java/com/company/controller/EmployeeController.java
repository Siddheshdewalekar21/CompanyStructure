package com.company.controller;

import com.company.entity.Employee;
import com.company.entity.FullTimeEmployee;
import com.company.entity.PartTimeEmployee;
import com.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Employee CRUD endpoints
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        try {
            Employee createdEmployee = employeeService.createEmployee(employee);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        return employeeService.getEmployeeByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employeeDetails) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            return ResponseEntity.ok(updatedEmployee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Full-time employee endpoints
    @PostMapping("/full-time")
    public ResponseEntity<FullTimeEmployee> createFullTimeEmployee(@Valid @RequestBody FullTimeEmployee employee) {
        try {
            FullTimeEmployee createdEmployee = employeeService.createFullTimeEmployee(employee);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/full-time")
    public ResponseEntity<List<FullTimeEmployee>> getAllFullTimeEmployees() {
        List<FullTimeEmployee> employees = employeeService.getAllFullTimeEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/full-time/min-bonus/{minBonus}")
    public ResponseEntity<List<FullTimeEmployee>> getFullTimeEmployeesByMinimumBonus(@PathVariable BigDecimal minBonus) {
        List<FullTimeEmployee> employees = employeeService.getFullTimeEmployeesByMinimumBonus(minBonus);
        return ResponseEntity.ok(employees);
    }

    // Part-time employee endpoints
    @PostMapping("/part-time")
    public ResponseEntity<PartTimeEmployee> createPartTimeEmployee(@Valid @RequestBody PartTimeEmployee employee) {
        try {
            PartTimeEmployee createdEmployee = employeeService.createPartTimeEmployee(employee);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/part-time")
    public ResponseEntity<List<PartTimeEmployee>> getAllPartTimeEmployees() {
        List<PartTimeEmployee> employees = employeeService.getAllPartTimeEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/part-time/min-hours/{minHours}")
    public ResponseEntity<List<PartTimeEmployee>> getPartTimeEmployeesByMinimumHours(@PathVariable Integer minHours) {
        List<PartTimeEmployee> employees = employeeService.getPartTimeEmployeesByMinimumHours(minHours);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/part-time/expired-contracts")
    public ResponseEntity<List<PartTimeEmployee>> getPartTimeEmployeesWithExpiredContracts() {
        List<PartTimeEmployee> employees = employeeService.getPartTimeEmployeesWithExpiredContracts();
        return ResponseEntity.ok(employees);
    }

    // Business logic endpoints
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        List<Employee> employees = employeeService.getEmployeesByDepartmentId(departmentId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Employee>> getEmployeesByCompanyId(@PathVariable Long companyId) {
        List<Employee> employees = employeeService.getEmployeesByCompanyId(companyId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployeesByName(@RequestParam String firstName, @RequestParam String lastName) {
        List<Employee> employees = employeeService.searchEmployeesByName(firstName, lastName);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/hire-date-range")
    public ResponseEntity<List<Employee>> getEmployeesByHireDateRange(
            @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Employee> employees = employeeService.getEmployeesByHireDateRange(start, end);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/salary-range")
    public ResponseEntity<List<Employee>> getEmployeesBySalaryRange(
            @RequestParam BigDecimal minSalary, @RequestParam BigDecimal maxSalary) {
        List<Employee> employees = employeeService.getEmployeesBySalaryRange(minSalary, maxSalary);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/department/{departmentId}/ordered-by-salary")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentOrderedBySalary(@PathVariable Long departmentId) {
        List<Employee> employees = employeeService.getEmployeesByDepartmentOrderedBySalary(departmentId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/salary-greater-than/{salary}")
    public ResponseEntity<List<Employee>> getEmployeesWithSalaryGreaterThan(@PathVariable BigDecimal salary) {
        List<Employee> employees = employeeService.getEmployeesWithSalaryGreaterThan(salary);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/department/{departmentId}/count")
    public ResponseEntity<Integer> getEmployeeCountByDepartmentId(@PathVariable Long departmentId) {
        int count = employeeService.getEmployeeCountByDepartmentId(departmentId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/company/{companyId}/count")
    public ResponseEntity<Integer> getEmployeeCountByCompanyId(@PathVariable Long companyId) {
        int count = employeeService.getEmployeeCountByCompanyId(companyId);
        return ResponseEntity.ok(count);
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Employee service is running");
    }
}