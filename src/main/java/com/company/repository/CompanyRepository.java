package com.company.repository;

import com.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByName(String name);
    
    List<Company> findByIndustry(String industry);
    
    List<Company> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT c FROM Company c WHERE c.departments.size > :minDepartments")
    List<Company> findCompaniesWithMinimumDepartments(@Param("minDepartments") int minDepartments);
    
    @Query("SELECT c FROM Company c JOIN c.departments d JOIN d.employees e GROUP BY c HAVING COUNT(e) > :minEmployees")
    List<Company> findCompaniesWithMinimumEmployees(@Param("minEmployees") int minEmployees);
    
    boolean existsByName(String name);
}