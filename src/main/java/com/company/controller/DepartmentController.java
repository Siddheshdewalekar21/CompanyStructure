package com.company.controller;

import com.company.entity.Department;
import com.company.entity.Employee;
import com.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Department CRUD endpoints
    @PostMapping
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) {
        try {
            Department createdDepartment = departmentService.createDepartment(department);
            return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @Valid @RequestBody Department departmentDetails) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, departmentDetails);
            return ResponseEntity.ok(updatedDepartment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Employee endpoints
    @PostMapping("/{departmentId}/employees")
    public ResponseEntity<Employee> addEmployeeToDepartment(@PathVariable Long departmentId, @Valid @RequestBody Employee employee) {
        try {
            Employee createdEmployee = departmentService.addEmployeeToDepartment(departmentId, employee);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{departmentId}/employees")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        List<Employee> employees = departmentService.getEmployeesByDepartmentId(departmentId);
        return ResponseEntity.ok(employees);
    }

    // Business logic endpoints
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Department>> getDepartmentsByLocation(@PathVariable String location) {
        List<Department> departments = departmentService.getDepartmentsByLocation(location);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Department>> searchDepartmentsByName(@RequestParam String name) {
        List<Department> departments = departmentService.searchDepartmentsByName(name);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/with-min-employees/{minEmployees}")
    public ResponseEntity<List<Department>> getDepartmentsWithMinimumEmployees(@PathVariable int minEmployees) {
        List<Department> departments = departmentService.getDepartmentsWithMinimumEmployees(minEmployees);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/ordered-by-employee-count")
    public ResponseEntity<List<Department>> getDepartmentsOrderedByEmployeeCount() {
        List<Department> departments = departmentService.getDepartmentsOrderedByEmployeeCount();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Department>> getDepartmentsByCompanyId(@PathVariable Long companyId) {
        List<Department> departments = departmentService.getDepartmentsByCompanyId(companyId);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{departmentId}/employee-count")
    public ResponseEntity<Integer> getEmployeeCountByDepartmentId(@PathVariable Long departmentId) {
        int count = departmentService.getEmployeeCountByDepartmentId(departmentId);
        return ResponseEntity.ok(count);
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Department service is running");
    }
}