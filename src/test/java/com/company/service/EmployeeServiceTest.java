package com.company.service;

import com.company.entity.*;
import com.company.repository.CompanyRepository;
import com.company.repository.DepartmentRepository;
import com.company.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({EmployeeService.class, DepartmentService.class, CompanyService.class})
class EmployeeServiceTest {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;

    private Company company;
    private Department department;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
        companyRepository.deleteAll();
        company = companyService.createCompany(new Company("TestCo", "desc", "IT", "addr"));
        department = new Department("Engineering", "desc", "HQ");
        department.setCompany(company);
        department = departmentService.createDepartment(department);
    }

    @Test
    void testCreateAndGetFullTimeEmployee() {
        FullTimeEmployee emp = new FullTimeEmployee("Alice", "Smith", "alice@company.com", LocalDate.now(), new BigDecimal("90000"), new BigDecimal("5000"));
        emp.setDepartment(department);
        emp.setJobTitle("Engineer");
        FullTimeEmployee saved = employeeService.createFullTimeEmployee(emp);
        assertNotNull(saved.getId());
        assertEquals("Alice", saved.getFirstName());
        assertEquals("Engineer", saved.getJobTitle());
        List<FullTimeEmployee> all = employeeService.getAllFullTimeEmployees();
        assertEquals(1, all.size());
    }

    @Test
    void testCreateAndGetPartTimeEmployee() {
        PartTimeEmployee emp = new PartTimeEmployee("Bob", "Jones", "bob@company.com", LocalDate.now(), new BigDecimal("30000"), 20);
        emp.setDepartment(department);
        emp.setJobTitle("Support");
        PartTimeEmployee saved = employeeService.createPartTimeEmployee(emp);
        assertNotNull(saved.getId());
        assertEquals("Bob", saved.getFirstName());
        assertEquals(20, saved.getHoursPerWeek());
        List<PartTimeEmployee> all = employeeService.getAllPartTimeEmployees();
        assertEquals(1, all.size());
    }

    @Test
    void testDuplicateEmailThrows() {
        FullTimeEmployee emp1 = new FullTimeEmployee("Carol", "White", "carol@company.com", LocalDate.now(), new BigDecimal("80000"), new BigDecimal("4000"));
        emp1.setDepartment(department);
        employeeService.createFullTimeEmployee(emp1);
        PartTimeEmployee emp2 = new PartTimeEmployee("Carol", "White", "carol@company.com", LocalDate.now(), new BigDecimal("20000"), 10);
        emp2.setDepartment(department);
        assertThrows(IllegalArgumentException.class, () -> employeeService.createPartTimeEmployee(emp2));
    }

    @Test
    void testDeleteEmployee() {
        FullTimeEmployee emp = new FullTimeEmployee("Dave", "Brown", "dave@company.com", LocalDate.now(), new BigDecimal("70000"), new BigDecimal("3000"));
        emp.setDepartment(department);
        FullTimeEmployee saved = employeeService.createFullTimeEmployee(emp);
        employeeService.deleteEmployee(saved.getId());
        assertFalse(employeeService.getEmployeeById(saved.getId()).isPresent());
    }
}