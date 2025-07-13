package com.company.service;

import com.company.entity.Company;
import com.company.entity.Department;
import com.company.repository.CompanyRepository;
import com.company.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, DepartmentRepository departmentRepository) {
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
    }

    // Company CRUD operations
    public Company createCompany(Company company) {
        if (companyRepository.existsByName(company.getName())) {
            throw new IllegalArgumentException("Company with name '" + company.getName() + "' already exists");
        }
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Optional<Company> getCompanyByName(String name) {
        return companyRepository.findByName(name);
    }

    public Company updateCompany(Long id, Company companyDetails) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + id));
        
        company.setName(companyDetails.getName());
        company.setDescription(companyDetails.getDescription());
        company.setIndustry(companyDetails.getIndustry());
        company.setAddress(companyDetails.getAddress());
        
        return companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new IllegalArgumentException("Company not found with id: " + id);
        }
        companyRepository.deleteById(id);
    }

    // Department operations
    public Department addDepartmentToCompany(Long companyId, Department department) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + companyId));
        
        if (departmentRepository.existsByNameAndCompanyId(department.getName(), companyId)) {
            throw new IllegalArgumentException("Department with name '" + department.getName() + "' already exists in this company");
        }
        
        department.setCompany(company);
        return departmentRepository.save(department);
    }

    public List<Department> getDepartmentsByCompanyId(Long companyId) {
        return departmentRepository.findByCompanyId(companyId);
    }

    public List<Department> getDepartmentsByCompanyName(String companyName) {
        Company company = companyRepository.findByName(companyName)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with name: " + companyName));
        return departmentRepository.findByCompanyId(company.getId());
    }

    // Business logic operations
    public List<Company> getCompaniesByIndustry(String industry) {
        return companyRepository.findByIndustry(industry);
    }

    public List<Company> searchCompaniesByName(String name) {
        return companyRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Company> getCompaniesWithMinimumDepartments(int minDepartments) {
        return companyRepository.findCompaniesWithMinimumDepartments(minDepartments);
    }

    public List<Company> getCompaniesWithMinimumEmployees(int minEmployees) {
        return companyRepository.findCompaniesWithMinimumEmployees(minEmployees);
    }

    public boolean companyExists(Long id) {
        return companyRepository.existsById(id);
    }

    public boolean companyExistsByName(String name) {
        return companyRepository.existsByName(name);
    }
}