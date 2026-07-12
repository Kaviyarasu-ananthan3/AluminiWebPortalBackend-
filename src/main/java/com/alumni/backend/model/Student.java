package com.alumni.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String department;
    private Integer year;
    private String interest;

    public Student() {
    }

    public Student(String name, String department, Integer year, String interest) {
        this.name = name;
        this.department = department;
        this.year = year;
        this.interest = interest;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public Integer getYear() {
        return year;
    }

    public String getInterest() {
        return interest;
    }
}
