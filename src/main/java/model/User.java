package com.alumni.alumniconnectportal.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String role;

    private String phone;

    private String department;

    private String batch;

    private String company;

    private String designation;

    private String experience;

    private String salary;

    private String location;

    private String skills;

    private String linkedin;

    private String year;

    private String cgpa;

    private String interests;


}