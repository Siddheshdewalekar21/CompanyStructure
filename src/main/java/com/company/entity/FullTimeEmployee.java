package com.company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("FULL_TIME")
public class FullTimeEmployee extends Employee {

    @NotNull(message = "Annual bonus is required")
    @Positive(message = "Annual bonus must be positive")
    @Column(name = "annual_bonus", nullable = false, precision = 10, scale = 2)
    private BigDecimal annualBonus;

    @Column(name = "stock_options")
    private Integer stockOptions;

    @Column(name = "health_insurance")
    private Boolean healthInsurance = true;

    @Column(name = "retirement_plan")
    private Boolean retirementPlan = true;

    // Constructors
    public FullTimeEmployee() {
    }

    public FullTimeEmployee(String firstName, String lastName, String email, LocalDate hireDate, 
                          BigDecimal salary, BigDecimal annualBonus) {
        super(firstName, lastName, email, hireDate, salary);
        this.annualBonus = annualBonus;
    }

    // Getters and Setters
    public BigDecimal getAnnualBonus() {
        return annualBonus;
    }

    public void setAnnualBonus(BigDecimal annualBonus) {
        this.annualBonus = annualBonus;
    }

    public Integer getStockOptions() {
        return stockOptions;
    }

    public void setStockOptions(Integer stockOptions) {
        this.stockOptions = stockOptions;
    }

    public Boolean getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(Boolean healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public Boolean getRetirementPlan() {
        return retirementPlan;
    }

    public void setRetirementPlan(Boolean retirementPlan) {
        this.retirementPlan = retirementPlan;
    }

    @Override
    public String getEmployeeType() {
        return "FULL_TIME";
    }

    // Helper methods
    public BigDecimal getTotalCompensation() {
        return getSalary().add(annualBonus != null ? annualBonus : BigDecimal.ZERO);
    }

    @Override
    public String toString() {
        return "FullTimeEmployee{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", salary=" + getSalary() +
                ", annualBonus=" + annualBonus +
                ", stockOptions=" + stockOptions +
                ", healthInsurance=" + healthInsurance +
                ", retirementPlan=" + retirementPlan +
                '}';
    }
}