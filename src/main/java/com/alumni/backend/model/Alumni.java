package com.alumni.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Alumni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer batchYear;
    private String department;
    private String company;
    private String role;
    private String skills;
    private String location;

    public Alumni() {
    }

    public Alumni(String name, Integer batchYear, String department, String company, String role, String skills, String location) {
        this.name = name;
        this.batchYear = batchYear;
        this.department = department;
        this.company = company;
        this.role = role;
        this.skills = skills;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBatchYear() {
        return batchYear;
    }

    public String getDepartment() {
        return department;
    }

    public String getCompany() {
        return company;
    }

    public String getRole() {
        return role;
    }

    public String getSkills() {
        return skills;
    }

    public String getLocation() {
        return location;
    }
}
