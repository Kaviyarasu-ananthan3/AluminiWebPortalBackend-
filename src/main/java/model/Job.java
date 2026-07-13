package com.alumni.alumniconnectportal.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    private String role;

    private String location;

    private String experience;

    private String salary;

    private String skills;

    private String lastDate;

    private String postedBy;

    private String email;// alumni name

}