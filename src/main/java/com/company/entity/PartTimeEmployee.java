package com.company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("PART_TIME")
public class PartTimeEmployee extends Employee {

    @NotNull(message = "Hours per week is required")
    @Positive(message = "Hours per week must be positive")
    @Column(name = "hours_per_week", nullable = false)
    private Integer hoursPerWeek;

    @Column(name = "flexible_schedule")
    private Boolean flexibleSchedule = false;

    @Column(name = "remote_work")
    private Boolean remoteWork = false;

    @Column(name = "contract_end_date")
    private LocalDate contractEndDate;

    // Constructors
    public PartTimeEmployee() {
    }

    public PartTimeEmployee(String firstName, String lastName, String email, LocalDate hireDate, 
                          BigDecimal salary, Integer hoursPerWeek) {
        super(firstName, lastName, email, hireDate, salary);
        this.hoursPerWeek = hoursPerWeek;
    }

    // Getters and Setters
    public Integer getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(Integer hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public Boolean getFlexibleSchedule() {
        return flexibleSchedule;
    }

    public void setFlexibleSchedule(Boolean flexibleSchedule) {
        this.flexibleSchedule = flexibleSchedule;
    }

    public Boolean getRemoteWork() {
        return remoteWork;
    }

    public void setRemoteWork(Boolean remoteWork) {
        this.remoteWork = remoteWork;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    @Override
    public String getEmployeeType() {
        return "PART_TIME";
    }

    // Helper methods
    public BigDecimal getHourlyRate() {
        if (hoursPerWeek != null && hoursPerWeek > 0) {
            return getSalary().divide(BigDecimal.valueOf(hoursPerWeek * 52), 2, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    public boolean isContractExpired() {
        return contractEndDate != null && contractEndDate.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return "PartTimeEmployee{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", salary=" + getSalary() +
                ", hoursPerWeek=" + hoursPerWeek +
                ", flexibleSchedule=" + flexibleSchedule +
                ", remoteWork=" + remoteWork +
                ", contractEndDate=" + contractEndDate +
                '}';
    }
}