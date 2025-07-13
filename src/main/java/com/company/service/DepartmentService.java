package com.company.service;

import com.company.entity.Department;
import com.company.entity.Employee;
import com.company.repository.DepartmentRepository;
import com.company.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    // Department CRUD operations
    public Department createDepartment(Department department) {
        if (department.getCompany() == null || department.getCompany().getId() == null) {
            throw new IllegalArgumentException("Department must be associated with a company");
        }
        
        if (departmentRepository.existsByNameAndCompanyId(department.getName(), department.getCompany().getId())) {
            throw new IllegalArgumentException("Department with name '" + department.getName() + "' already exists in this company");
        }
        
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id: " + id));
        
        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());
        department.setLocation(departmentDetails.getLocation());
        
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }

    // Employee operations
    public Employee addEmployeeToDepartment(Long departmentId, Employee employee) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id: " + departmentId));
        
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Employee with email '" + employee.getEmail() + "' already exists");
        }
        
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    public List<Employee> getEmployeesByDepartmentName(String departmentName) {
        Department department = departmentRepository.findByNameAndCompanyId(departmentName, null)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with name: " + departmentName));
        return employeeRepository.findByDepartmentId(department.getId());
    }

    // Business logic operations
    public List<Department> getDepartmentsByLocation(String location) {
        return departmentRepository.findByLocation(location);
    }

    public List<Department> searchDepartmentsByName(String name) {
        return departmentRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Department> getDepartmentsWithMinimumEmployees(int minEmployees) {
        return departmentRepository.findDepartmentsWithMinimumEmployees(minEmployees);
    }

    public List<Department> getDepartmentsOrderedByEmployeeCount() {
        return departmentRepository.findDepartmentsOrderedByEmployeeCount();
    }

    public List<Department> getDepartmentsByCompanyId(Long companyId) {
        return departmentRepository.findByCompanyId(companyId);
    }

    public boolean departmentExists(Long id) {
        return departmentRepository.existsById(id);
    }

    public boolean departmentExistsByNameAndCompanyId(String name, Long companyId) {
        return departmentRepository.existsByNameAndCompanyId(name, companyId);
    }

    public int getEmployeeCountByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).size();
    }
}