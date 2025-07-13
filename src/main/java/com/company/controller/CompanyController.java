package com.company.controller;

import com.company.entity.Company;
import com.company.entity.Department;
import com.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // Company CRUD endpoints
    @PostMapping
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Company> getCompanyByName(@PathVariable String name) {
        return companyService.getCompanyByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @Valid @RequestBody Company companyDetails) {
        try {
            Company updatedCompany = companyService.updateCompany(id, companyDetails);
            return ResponseEntity.ok(updatedCompany);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Department endpoints
    @PostMapping("/{companyId}/departments")
    public ResponseEntity<Department> addDepartmentToCompany(@PathVariable Long companyId, @Valid @RequestBody Department department) {
        try {
            Department createdDepartment = companyService.addDepartmentToCompany(companyId, department);
            return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{companyId}/departments")
    public ResponseEntity<List<Department>> getDepartmentsByCompanyId(@PathVariable Long companyId) {
        List<Department> departments = companyService.getDepartmentsByCompanyId(companyId);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/name/{companyName}/departments")
    public ResponseEntity<List<Department>> getDepartmentsByCompanyName(@PathVariable String companyName) {
        try {
            List<Department> departments = companyService.getDepartmentsByCompanyName(companyName);
            return ResponseEntity.ok(departments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Business logic endpoints
    @GetMapping("/industry/{industry}")
    public ResponseEntity<List<Company>> getCompaniesByIndustry(@PathVariable String industry) {
        List<Company> companies = companyService.getCompaniesByIndustry(industry);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Company>> searchCompaniesByName(@RequestParam String name) {
        List<Company> companies = companyService.searchCompaniesByName(name);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/with-min-departments/{minDepartments}")
    public ResponseEntity<List<Company>> getCompaniesWithMinimumDepartments(@PathVariable int minDepartments) {
        List<Company> companies = companyService.getCompaniesWithMinimumDepartments(minDepartments);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/with-min-employees/{minEmployees}")
    public ResponseEntity<List<Company>> getCompaniesWithMinimumEmployees(@PathVariable int minEmployees) {
        List<Company> companies = companyService.getCompaniesWithMinimumEmployees(minEmployees);
        return ResponseEntity.ok(companies);
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Company service is running");
    }
}