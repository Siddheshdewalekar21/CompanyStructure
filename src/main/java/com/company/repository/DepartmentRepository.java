package com.company.repository;

import com.company.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByCompanyId(Long companyId);
    
    Optional<Department> findByNameAndCompanyId(String name, Long companyId);
    
    List<Department> findByLocation(String location);
    
    List<Department> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT d FROM Department d WHERE d.employees.size > :minEmployees")
    List<Department> findDepartmentsWithMinimumEmployees(@Param("minEmployees") int minEmployees);
    
    @Query("SELECT d FROM Department d JOIN d.employees e GROUP BY d ORDER BY COUNT(e) DESC")
    List<Department> findDepartmentsOrderedByEmployeeCount();
    
    boolean existsByNameAndCompanyId(String name, Long companyId);
}