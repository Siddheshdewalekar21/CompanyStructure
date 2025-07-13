package com.company.service;

import com.company.entity.Company;
import com.company.entity.Department;
import com.company.entity.Employee;
import com.company.repository.CompanyRepository;
import com.company.repository.DepartmentRepository;
import com.company.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({DepartmentService.class, CompanyService.class})
class DepartmentServiceTest {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;

    private Company company;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
        companyRepository.deleteAll();
        company = companyService.createCompany(new Company("TestCo", "desc", "IT", "addr"));
    }

    @Test
    void testCreateAndGetDepartment() {
        Department department = new Department("Engineering", "desc", "HQ");
        department.setCompany(company);
        Department saved = departmentService.createDepartment(department);
        assertNotNull(saved.getId());
        assertEquals("Engineering", saved.getName());
        assertEquals(company.getId(), saved.getCompany().getId());
    }

    @Test
    void testDuplicateDepartmentNameThrows() {
        Department department = new Department("Sales", "desc", "HQ");
        department.setCompany(company);
        departmentService.createDepartment(department);
        Department duplicate = new Department("Sales", "desc2", "HQ");
        duplicate.setCompany(company);
        assertThrows(IllegalArgumentException.class, () ->
            departmentService.createDepartment(duplicate)
        );
    }

    @Test
    void testGetDepartmentsByCompanyId() {
        Department department = new Department("Support", "desc", "HQ");
        department.setCompany(company);
        departmentService.createDepartment(department);
        List<Department> depts = departmentService.getDepartmentsByCompanyId(company.getId());
        assertEquals(1, depts.size());
        assertEquals("Support", depts.get(0).getName());
    }

    @Test
    void testDeleteDepartment() {
        Department department = new Department("DeleteDept", "desc", "HQ");
        department.setCompany(company);
        Department saved = departmentService.createDepartment(department);
        departmentService.deleteDepartment(saved.getId());
        assertFalse(departmentService.getDepartmentById(saved.getId()).isPresent());
    }
}