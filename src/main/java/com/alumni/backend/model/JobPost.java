package com.alumni.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String company;
    private String location;
    private String type;

    @Column(length = 1000)
    private String description;

    public JobPost() {
    }

    public JobPost(String title, String company, String location, String type, String description) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.type = type;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
