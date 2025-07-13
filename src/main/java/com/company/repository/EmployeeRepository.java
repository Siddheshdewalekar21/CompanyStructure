package com.company.repository;

import com.company.entity.Employee;
import com.company.entity.FullTimeEmployee;
import com.company.entity.PartTimeEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartmentId(Long departmentId);
    
    List<Employee> findByCompanyId(Long companyId);
    
    Optional<Employee> findByEmail(String email);
    
    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    
    List<Employee> findByHireDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Employee> findBySalaryGreaterThan(BigDecimal salary);
    
    @Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :minSalary AND :maxSalary")
    List<Employee> findEmployeesBySalaryRange(@Param("minSalary") BigDecimal minSalary, @Param("maxSalary") BigDecimal maxSalary);
    
    @Query("SELECT e FROM Employee e WHERE e.department.company.id = :companyId")
    List<Employee> findEmployeesByCompanyId(@Param("companyId") Long companyId);
    
    @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId ORDER BY e.salary DESC")
    List<Employee> findEmployeesByDepartmentOrderedBySalary(@Param("departmentId") Long departmentId);
    
    // Full-time employee specific queries
    @Query("SELECT e FROM FullTimeEmployee e")
    List<FullTimeEmployee> findAllFullTimeEmployees();
    
    @Query("SELECT e FROM FullTimeEmployee e WHERE e.annualBonus > :minBonus")
    List<FullTimeEmployee> findFullTimeEmployeesByMinimumBonus(@Param("minBonus") BigDecimal minBonus);
    
    // Part-time employee specific queries
    @Query("SELECT e FROM PartTimeEmployee e")
    List<PartTimeEmployee> findAllPartTimeEmployees();
    
    @Query("SELECT e FROM PartTimeEmployee e WHERE e.hoursPerWeek >= :minHours")
    List<PartTimeEmployee> findPartTimeEmployeesByMinimumHours(@Param("minHours") Integer minHours);
    
    @Query("SELECT e FROM PartTimeEmployee e WHERE e.contractEndDate < :date")
    List<PartTimeEmployee> findPartTimeEmployeesWithExpiredContracts(@Param("date") LocalDate date);
    
    boolean existsByEmail(String email);
}