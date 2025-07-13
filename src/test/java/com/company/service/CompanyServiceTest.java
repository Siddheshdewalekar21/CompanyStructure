package com.company.service;

import com.company.entity.Company;
import com.company.entity.Department;
import com.company.repository.CompanyRepository;
import com.company.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CompanyService.class)
class CompanyServiceTest {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        companyRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    void testCreateAndGetCompany() {
        Company company = new Company("TestCo", "Test Description", "IT", "Test Address");
        Company saved = companyService.createCompany(company);
        assertNotNull(saved.getId());
        Optional<Company> found = companyService.getCompanyById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("TestCo", found.get().getName());
    }

    @Test
    void testDuplicateCompanyNameThrows() {
        companyService.createCompany(new Company("UniqueCo", "desc", "IT", "addr"));
        assertThrows(IllegalArgumentException.class, () ->
            companyService.createCompany(new Company("UniqueCo", "desc2", "IT", "addr2"))
        );
    }

    @Test
    void testAddDepartmentToCompany() {
        Company company = companyService.createCompany(new Company("DeptCo", "desc", "IT", "addr"));
        Department department = new Department("Engineering", "desc", "HQ");
        Department savedDept = companyService.addDepartmentToCompany(company.getId(), department);
        assertNotNull(savedDept.getId());
        List<Department> depts = companyService.getDepartmentsByCompanyId(company.getId());
        assertEquals(1, depts.size());
        assertEquals("Engineering", depts.get(0).getName());
    }

    @Test
    void testDeleteCompany() {
        Company company = companyService.createCompany(new Company("DeleteCo", "desc", "IT", "addr"));
        Long id = company.getId();
        companyService.deleteCompany(id);
        assertFalse(companyService.getCompanyById(id).isPresent());
    }
}